package com.example.myapplication.net;

import com.example.myapplication.Constants;
import com.example.myapplication.log.SpeedyLog;
import org.json.JSONObject;
import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.charset.UnsupportedCharsetException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : simkey-pivot
 * @Package : com.ctq.simkey.app_https.interceptor
 * @ClassName : ResponseInterceptor
 * @Description : 返回拦截器(响应拦截器)
 * @Author : Abner(zt)
 * @CreateDate : 2021/11/22 10:51
 * @UpdateUser : 更新者
 * @UpdateDate : 2021/11/22 10:51
 * @UpdateRemark : 更新说明
 */
public class ResponseInterceptor implements Interceptor {

    static final String TAG = Constants.BASE_TAG + ResponseInterceptor.class.getSimpleName();
    private static final String CODE_NAME = "code";

    @Override
    public Response intercept(Chain chain) throws IOException {
        HttpUrl httpUrl = chain.request().url();
        Request.Builder requestBuilder = chain.request().newBuilder();
        Response originResponse = chain.proceed(requestBuilder.build());

        if (!isNeedIntercept(httpUrl)) {
            return originResponse;
        }

        ResponseBody responseBody = originResponse.body();
        long contentLength = responseBody.contentLength();

        if (!bodyEncoded(originResponse.headers())) {
            BufferedSource source = responseBody.source();
            source.request(Long.MAX_VALUE); // Buffer the entire body.
            Buffer buffer = source.buffer();
            Charset charset = StandardCharsets.UTF_8;
            MediaType contentType = responseBody.contentType();
            if (contentType != null) {
                try {
                    charset = contentType.charset(StandardCharsets.UTF_8);
                } catch (UnsupportedCharsetException e) {
                    return originResponse;
                }
            }
            if (!isPlaintext(buffer)) {
                return originResponse;
            }
        }

        return originResponse;
    }

    /**
     * 是否需要拦截请求
     */
    private boolean isNeedIntercept(HttpUrl httpUrl) {
        String requestPath = httpUrl.pathSegments().stream().reduce("", (a, b) -> a + "/" + b);

        // 拦截身份认证相关需要进行 token 认证以及进行密钥申请相关的接口
        String[] isNeedInterceptPaths = {

        };

        List<String> isNeedInterceptPathList = Arrays.stream(isNeedInterceptPaths)
                .parallel()
                .map(path -> "/" + path)
                .collect(Collectors.toList());

        boolean isNeedIntercept = isNeedInterceptPathList.contains(requestPath);
        if (!isNeedIntercept) {
            SpeedyLog.i(TAG, "%s 接口请求不需要拦截，返回原始响应", requestPath);
        }
        return isNeedIntercept;
    }

    /**
     * 根据Response，判断Token是否失效
     */
    private int getResponseCode(String responseBody) {
        try {
            JSONObject json = new JSONObject(responseBody);
            int code = json.optInt(CODE_NAME);
            SpeedyLog.i(TAG, "getResponseCode：" + code);
            return code;
        } catch (Throwable e) {
            SpeedyLog.i(TAG, "getResponseCode 异常", e);
        }
        return -1;
    }

    private boolean isPlaintext(Buffer buffer) {
        try {
            Buffer prefix = new Buffer();
            long byteCount = buffer.size() < 64 ? buffer.size() : 64;
            buffer.copyTo(prefix, 0, byteCount);
            for (int i = 0; i < 16; i++) {
                if (prefix.exhausted()) {
                    break;
                }
                int codePoint = prefix.readUtf8CodePoint();
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false;
                }
            }
            return true;
        } catch (EOFException e) {
            return false; // Truncated UTF-8 sequence.
        }
    }

    private boolean bodyEncoded(Headers headers) {
        String contentEncoding = headers.get("Content-Encoding");
        return contentEncoding != null && !contentEncoding.equalsIgnoreCase("identity");
    }
}

