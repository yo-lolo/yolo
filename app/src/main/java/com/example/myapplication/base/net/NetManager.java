package com.example.myapplication.base.net;

import com.example.myapplication.common.Constants;
import com.example.myapplication.base.log.SpeedyLog;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : simkey-pivot
 * @Package : com.ctq.simkey.pivot.common.net
 * @ClassName : NetManager
 * @Description : 网络请求的管理类
 * @Author : Abner(zt)
 * @CreateDate : 2021/11/22 11:32
 * @UpdateUser : 更新者
 * @UpdateDate : 2021/11/22 11:32
 * @UpdateRemark : 更新说明
 */
public class NetManager {
    static final String TAG = Constants.BASE_TAG + NetManager.class.getSimpleName();
    /**
     * 获取APP运行状态及版本信息，用于日志打印
     */
    private static AppBaseNetInfo appNetInfo;

    /**
     * API访问地址
     */
    public static String BASE_URL = "http://192.168.2.112:9529/";

    private static OkHttpClient okHttpClient;

    private static final HashMap<String, Retrofit> retrofitHashMap = new HashMap<>();

    /**
     * 初始化
     */
    public static void init(AppBaseNetInfo appBaseNetInfo) {
        appNetInfo = appBaseNetInfo;
    }

    /**
     * 创建serviceClass的实例
     */
    public static <T> T createService(Class<T> serviceClass, int type) {
        //设置Url类型
        setUrlType(type);
        return getRetrofit(serviceClass).create(serviceClass);
    }

    /**
     * 设置访问Url类型
     *
     * @param type 0 基础
     */
    private static void setUrlType(int type) {
        switch (type) {
            case 0:
                //基础
                BASE_URL = "http://192.168.2.196:9529/";
                break;
            default:
                break;
        }
    }

    /**
     * 配置OkHttp
     *
     * @return OkHttpClient
     */
    private static OkHttpClient getOkHttpClient() {
        //不为空则说明已经配置过了，直接返回即可。
        if (okHttpClient == null) {
            //OkHttp构建器
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            //设置缓存大小
            int cacheSize = 100 * 1024 * 1024;
            //设置网络请求超时时长，这里设置为45s
            builder.connectTimeout(45, TimeUnit.SECONDS);
            builder.writeTimeout(45, TimeUnit.SECONDS);
            builder.readTimeout(45, TimeUnit.SECONDS);
            //添加请求拦截器，如果接口有请求头的话，可以放在这个拦截器里面
            builder.addInterceptor(new RequestInterceptor(appNetInfo));
            //添加返回拦截器，可用于查看接口的请求耗时，对于网络优化有帮助
            builder.addInterceptor(new ResponseInterceptor());
            //当程序在debug过程中则打印数据日志，方便调试用。
//            if (appNetInfo != null && appNetInfo.isDebug()) {
            //暂时release也打印日志
            if (appNetInfo != null) {
                // iNetworkRequiredInfo不为空且处于debug状态下则初始化日志拦截器
                MyHttpLoggingInterceptor myHttpLoggingInterceptor = new MyHttpLoggingInterceptor(s -> {
                    try {
                        if (s.length() > 10 * 10000) {
                            // 超过 10w 字符时
                            SpeedyLog.i(TAG, "当前网络请求日志信息过长，不进行全量打印！");
                            SpeedyLog.i(0, TAG, s.substring(0, 10 * 10000));
                        } else {
                            SpeedyLog.i(0, TAG, s);
                        }
                    } catch (Throwable e) {
                        SpeedyLog.i(TAG, "网络请求日志日志打印异常", e);
                    }
                });
                // 设置要打印日志的内容等级，BODY为主要内容，还有BASIC、HEADERS、NONE。
                myHttpLoggingInterceptor.setLevel(MyHttpLoggingInterceptor.Level.BODY);
                // 将拦截器添加到OkHttp构建器中
                builder.addInterceptor(myHttpLoggingInterceptor);
            }
            //OkHttp配置完成
            okHttpClient = builder.build();
        }
        return okHttpClient;
    }

    /**
     * 配置Retrofit
     *
     * @param serviceClass 服务类
     * @return Retrofit
     */
    private static Retrofit getRetrofit(Class serviceClass) {
        if (retrofitHashMap.get(BASE_URL + serviceClass.getName()) != null) {
            //刚才上面定义的Map中键是String，值是Retrofit，当键不为空时，必然有值，有值则直接返回。
            return retrofitHashMap.get(BASE_URL + serviceClass.getName());
        }
        //初始化Retrofit  Retrofit是对OKHttp的封装，通常是对网络请求做处理，也可以处理返回数据。
        //Retrofit构建器
        Retrofit.Builder builder = new Retrofit.Builder();
        //设置访问地址
        builder.baseUrl(BASE_URL);
        //设置OkHttp客户端，传入上面写好的方法即可获得配置后的OkHttp客户端。
        builder.client(getOkHttpClient());
        //设置数据解析器 会自动把请求返回的结果（json字符串）通过Gson转化工厂自动转化成与其结构相符的实体Bean
        builder.addConverterFactory(GsonConverterFactory.create());
        //设置请求回调，使用RxJava 对网络返回进行处理
        builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        //retrofit配置完成
        Retrofit retrofit = builder.build();
        //放入Map中
        retrofitHashMap.put(BASE_URL + serviceClass.getName(), retrofit);
        //最后返回即可
        return retrofit;
    }


    /**
     * 对象转RequestBody
     */
    public static RequestBody beanToBody(Object bean) {
        Gson gson = new Gson();
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), gson.toJson(bean));
        return body;
    }
}

