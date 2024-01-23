package com.example.yolo_sdk.biometric;


/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : simkey-sdk-app
 * @Package : com.ctq.simkey.sdk.callback
 * @ClassName : SIMBiometricsCallback
 * @Description : 文件描述
 * @Author : Abner(zt)
 * @CreateDate : 2021/11/8 11:26
 * @UpdateUser : 更新者
 * @UpdateDate : 2021/11/8 11:26
 * @UpdateRemark : 更新说明
 */
public interface FingerprintCallback {
    /**
     * 指纹检查返回状态
     */
    void onFingerCheckCallback(String des);
}
