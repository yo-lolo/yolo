package com.example.myapplication.net;

import android.app.Application;

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : simkey-pivot
 * @Package : com.ctq.simkey.app_https.inf
 * @ClassName : AppBaseNetInfo
 * @Description : 文件描述
 * @Author : Abner(zt)
 * @CreateDate : 2021/11/22 10:51
 * @UpdateUser : 更新者
 * @UpdateDate : 2021/11/22 10:51
 * @UpdateRemark : 更新说明
 */
public interface AppBaseNetInfo {
    /**
     * 获取App版本名
     */
    String getAppVersionName();

    /**
     * 获取App版本号
     */
    String getAppVersionCode();

    /**
     * 判断是否为Debug模式
     */
    boolean isDebug();

    /**
     * 获取全局上下文参数
     */
    Application getApplicationContext();
}
