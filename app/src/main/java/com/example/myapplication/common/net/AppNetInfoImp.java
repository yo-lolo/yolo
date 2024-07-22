package com.example.myapplication.common.net;

import android.app.Application;

import com.example.myapplication.BuildConfig;


/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : simkey-pivot
 * @Package : com.ctq.simkey.pivot.common.net
 * @ClassName : AppNetInfoImp
 * @Description : 文件描述
 * @Author : Abner(zt)
 * @CreateDate : 2021/11/22 14:51
 * @UpdateUser : 更新者
 * @UpdateDate : 2021/11/22 14:51
 * @UpdateRemark : 更新说明
 */
public class AppNetInfoImp implements AppBaseNetInfo {

    private final Application application;

    public AppNetInfoImp(Application application){
        this.application = application;
    }

    @Override
    public String getAppVersionName() {
        return BuildConfig.VERSION_NAME;
    }

    @Override
    public String getAppVersionCode() {
        return String.valueOf(BuildConfig.VERSION_CODE);
    }

    @Override
    public boolean isDebug() {
        return BuildConfig.DEBUG;
    }

    @Override
    public Application getApplicationContext() {
        return application;
    }
}
