package com.example.yolo_sdk.biometric;

import android.app.Application;
import android.os.Build;

import androidx.annotation.RequiresApi;

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : simkey-sdk-app
 * @Package : com.ctq.simkey.sdk.func.biometric
 * @ClassName : FingerManagerBuilder
 * @Description : 文件描述
 * @Author : Abner(zt)
 * @CreateDate : 2022/7/19 9:13
 * @UpdateUser : 更新者
 * @UpdateDate : 2022/7/19 9:13
 * @UpdateRemark : 更新说明
 */
@RequiresApi(api = Build.VERSION_CODES.P)
public class FingerManagerBuilder {

    private Application mApplication;

    //弹窗标题
    private String mTitle;

    //弹窗描述
    private String mDes;

    //取消按钮话术
    private String mNegativeText;

    //指纹识别回调
    private IFingerCallback mFingerCallback;

    public FingerManagerBuilder setApplication(Application application) {
        mApplication = application;
        return this;
    }

    public Application getApplication() {
        return mApplication;
    }

    public FingerManagerBuilder setTitle(String title) {
        mTitle = title;
        return this;
    }

    public String getTitle() {
        return mTitle;
    }

    public FingerManagerBuilder setDes(String des) {
        this.mDes = des;
        return this;
    }

    public String getDes() {
        return mDes;
    }

    public FingerManagerBuilder setNegativeText(String negativeText) {
        this.mNegativeText = negativeText;
        return this;
    }

    public String getNegativeText() {
        return mNegativeText;
    }

    public FingerManagerBuilder setFingerCallback(IFingerCallback fingerCallback) {
        this.mFingerCallback = fingerCallback;
        return this;
    }

    public IFingerCallback getFingerCallback() {
        return mFingerCallback;
    }


    public FingerManager create() {
        if (mFingerCallback == null) {
            throw new RuntimeException("CompatFingerManager : FingerCheckCallback can not be null");
        }

        return FingerManager.getInstance(this);
    }

}
