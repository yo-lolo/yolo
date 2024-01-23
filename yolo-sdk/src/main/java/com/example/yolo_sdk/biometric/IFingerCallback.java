package com.example.yolo_sdk.biometric;

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : simkey-sdk-app
 * @Package : com.ctq.simkey.sdk.func.biometric
 * @ClassName : IFingerCallback
 * @Description : 文件描述
 * @Author : Abner(zt)
 * @CreateDate : 2022/7/19 9:12
 * @UpdateUser : 更新者
 * @UpdateDate : 2022/7/19 9:12
 * @UpdateRemark : 更新说明
 */
public interface IFingerCallback {
    //指纹错误
    void onError(int code,String error);
    //指纹帮助
    void onHelp(int code,String help);
    //指纹成功
    void onSucceed();
    //指纹失败
    void onFailed();
    //指纹取消
    void onCancel();
    //监听指纹变化
    void onChange();
}
