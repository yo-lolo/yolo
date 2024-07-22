package com.example.myapplication.common.net;


import com.example.myapplication.util.DateUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : simkey-pivot
 * @Package : com.ctq.simkey.app_https.interceptor
 * @ClassName : RequestInterceptor
 * @Description : 请求拦截器
 * @Author : Abner(zt)
 * @CreateDate : 2021/11/22 10:48
 * @UpdateUser : 更新者
 * @UpdateDate : 2021/11/22 10:48
 * @UpdateRemark : 更新说明
 */
public class RequestInterceptor implements Interceptor {
    /**
     * 网络请求信息
     */
    private AppBaseNetInfo appBaseNetInfo;

    public RequestInterceptor(AppBaseNetInfo appBaseNetInfo){
        this.appBaseNetInfo = appBaseNetInfo;
    }

    /**
     * 拦截
     */
    @Override
    public Response intercept(Chain chain) throws IOException {
        String nowDateTime = DateUtil.getDateTime();
        //构建器
        Request.Builder builder = chain.request().newBuilder();
        //添加使用环境
        builder.addHeader("os","android");
        //添加版本号
        builder.addHeader("appVersionCode",this.appBaseNetInfo.getAppVersionCode());
        //添加版本名
        builder.addHeader("appVersionName",this.appBaseNetInfo.getAppVersionName());
        //添加日期时间
        builder.addHeader("datetime",nowDateTime);
//        //添加请求头
//        builder.addHeader("Content-Type","application/json");
        //返回
        return chain.proceed(builder.build());
    }
}
