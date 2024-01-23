package com.example.yolo_sdk.biometric;

import android.os.CancellationSignal;

import androidx.annotation.NonNull;

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : simkey-sdk-app
 * @Package : com.ctq.simkey.sdk.func.biometric
 * @ClassName : IBiometricPrompt
 * @Description : 指纹认证接口
 * @Author : Abner(zt)
 * @CreateDate : 2022/7/19 9:12
 * @UpdateUser : 更新者
 * @UpdateDate : 2022/7/19 9:12
 * @UpdateRemark : 更新说明
 */
public interface IBiometricPrompt {
    void authenticate(@NonNull CancellationSignal cancel);
}
