package com.example.yolo_sdk.biometric;

import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.hardware.fingerprint.FingerprintManagerCompat;

import javax.crypto.Cipher;

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : simkey-sdk-app
 * @Package : com.ctq.simkey.sdk.func.biometric
 * @ClassName : FingerManager
 * @Description : 文件描述
 * @Author : Abner(zt)
 * @CreateDate : 2022/7/19 9:13
 * @UpdateUser : 更新者
 * @UpdateDate : 2022/7/19 9:13
 * @UpdateRemark : 更新说明
 */
@RequiresApi(api = Build.VERSION_CODES.P)
public class FingerManager {

    private static FingerManager fingerManager;

    private static FingerManagerBuilder mFingerManagerBuilder;

    private CancellationSignal cancellationSignal;

    private IBiometricPrompt biometricPrompt;

    public enum SupportResult {
        DEVICE_UNSUPPORTED,//设备不支持指纹识别
        SUPPORT_WITHOUT_DATA,//设备支持指纹识别但是没有指纹数据
        SUPPORT//设备支持且有指纹数据
    }

    private static FingerManager getInstance() {
        if (fingerManager == null) {
            synchronized (FingerManager.class) {
                if (fingerManager == null) {
                    fingerManager = new FingerManager();
                }
            }
        }
        return fingerManager;
    }

    public static FingerManager getInstance(FingerManagerBuilder fingerManagerBuilder) {
        mFingerManagerBuilder = fingerManagerBuilder;
        return getInstance();
    }

    /**
     * 检查设别是否支持指纹识别
     *
     * @return
     */
    public static SupportResult checkSupport(Context context) {
//		FingerprintManagerCompat fingerprintManager = FingerprintManagerCompat.from(context);
        FingerprintManager fingerprintManager = context.getSystemService(FingerprintManager.class);
        if (fingerprintManager != null && fingerprintManager.isHardwareDetected()) {
            if (fingerprintManager.hasEnrolledFingerprints()) {
                return SupportResult.SUPPORT;
            } else {
                return SupportResult.SUPPORT_WITHOUT_DATA;
            }
        } else {
            return SupportResult.DEVICE_UNSUPPORTED;
        }
    }

    /**
     * 开始监听指纹识别器
     */
    public void startListener(AppCompatActivity activity) {
        createImpl(activity);
        startListener();
    }

    private void createImpl(AppCompatActivity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            biometricPrompt = new BiometricPromptImpl28(activity, mFingerManagerBuilder);
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void startListener() {
        //如果还没有开启指纹数据变化监听，但是指纹数据已经发生了改变，就清除指纹数据变化，重新生成指纹加密库key
        if (!SharePreferenceUtil.isEnableFingerDataChange(mFingerManagerBuilder.getApplication()) && hasFingerprintChang(mFingerManagerBuilder.getApplication())) {
            updateFingerData(mFingerManagerBuilder.getApplication());
        } else {
            CipherHelper.getInstance().createKey(mFingerManagerBuilder.getApplication(), false);
        }

        if (cancellationSignal == null) {
            cancellationSignal = new CancellationSignal();
        }

        if (cancellationSignal.isCanceled()) {
            cancellationSignal = new CancellationSignal();
        }
        //开始指纹认证
        biometricPrompt.authenticate(cancellationSignal);
    }

    /**
     * 同步指纹数据,解除指纹数据变化问题
     *
     * @param context
     */
    public static void updateFingerData(Context context) {
        CipherHelper.getInstance().createKey(context, true);
        SharePreferenceUtil.saveEnableFingerDataChange(context, false);
        SharePreferenceUtil.saveFingerDataChange(context, false);
    }

    /**
     * 检查设备是否有指纹变化(例外：三星手机必须识别成功回调是才能检测到指纹库是否发生变化)
     *
     * @return
     */
    public static boolean hasFingerprintChang(Context context) {
        if (SharePreferenceUtil.isFingerDataChange(context)) {
            return true;
        }
        CipherHelper.getInstance().createKey(context, false);
        Cipher cipher = CipherHelper.getInstance().createCipher();
        return CipherHelper.getInstance().initCipher(cipher);
    }

    /**
     * 检查设备是否有支持指纹识别
     *
     * @return
     */
    public static boolean isHardwareDetected(Context context) {
        FingerprintManagerCompat fingerprintManagerCompat = FingerprintManagerCompat.from(context);
        return fingerprintManagerCompat.isHardwareDetected();
    }

    /**
     * 检查设备是否有指纹数据
     *
     * @return
     */
    public static boolean hasFingerprintData(Context context) {
        FingerprintManagerCompat fingerprintManagerCompat = FingerprintManagerCompat.from(context);
        return fingerprintManagerCompat.hasEnrolledFingerprints();
    }

    public static FingerManagerBuilder build() {
        return new FingerManagerBuilder();
    }
}
