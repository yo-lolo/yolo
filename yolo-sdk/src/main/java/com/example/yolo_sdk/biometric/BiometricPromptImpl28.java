package com.example.yolo_sdk.biometric;

import android.content.DialogInterface;
import android.hardware.biometrics.BiometricPrompt;
import android.os.Build;
import android.os.CancellationSignal;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import javax.crypto.Cipher;

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : simkey-sdk-app
 * @Package : com.ctq.simkey.sdk.func.biometric
 * @ClassName : BiometricPromptImpl28
 * @Description : Android 9.0及以上的指纹认证实现
 * @Author : Abner(zt)
 * @CreateDate : 2022/7/19 9:12
 * @UpdateUser : 更新者
 * @UpdateDate : 2022/7/19 9:12
 * @UpdateRemark : 更新说明
 */
@RequiresApi(Build.VERSION_CODES.P)
public class BiometricPromptImpl28 implements IBiometricPrompt {

    private AppCompatActivity mActivity;
    private CancellationSignal mCancellationSignal;
    private boolean mSelfCanceled;//用户主动取消指纹识别
    private Cipher cipher;
    private IFingerCallback mFingerCallback;
    private BiometricPrompt mBiometricPrompt;
    private static final String SECRET_MESSAGE = "Very secret message";

    @RequiresApi(Build.VERSION_CODES.P)
    public BiometricPromptImpl28(AppCompatActivity activity, FingerManagerBuilder fingerManagerController) {
        this.mActivity = activity;
        this.cipher = CipherHelper.getInstance().createCipher();
        this.mFingerCallback = fingerManagerController.getFingerCallback();
        //Android 9.0及以下显示系统的指纹认证对话框
        this.mBiometricPrompt = new BiometricPrompt
                .Builder(activity)
                .setTitle(fingerManagerController.getTitle())
                .setDescription(fingerManagerController.getDes())
                .setNegativeButton(fingerManagerController.getNegativeText(),
                        activity.getMainExecutor(), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
//                                mSelfCanceled = true;
//                                mFingerCallback.onCancel();
//                                mCancellationSignal.cancel();
                            }
                        })
                .build();
    }

    /**
     * 开始指纹认证
     *
     * @param cancel
     */
    @RequiresApi(Build.VERSION_CODES.P)
    @Override
    public void authenticate(@Nullable final CancellationSignal cancel) {
        mSelfCanceled = false;
        mCancellationSignal = cancel;
        //检测指纹库是否发生变化
        boolean exceptionState = CipherHelper.getInstance().initCipher(cipher);
        boolean flag = SharePreferenceUtil.isEnableFingerDataChange(mActivity) && (exceptionState || SharePreferenceUtil.isFingerDataChange(mActivity));
        if (flag) {
            SharePreferenceUtil.saveFingerDataChange(mActivity, true);
            mFingerCallback.onChange();
            return;
        }
        //开始指纹认证
        try {
            mBiometricPrompt.authenticate(new BiometricPrompt.CryptoObject(cipher),
                    cancel, mActivity.getMainExecutor(), new BiometricPrompt.AuthenticationCallback() {
                        @Override
                        public void onAuthenticationError(int errorCode, CharSequence errString) {
                            // 指纹验证多次错误的时候回调方法，多次尝试都失败了的时候，errString为错误信息
                            super.onAuthenticationError(errorCode, errString);
//                        Toast.makeText(mActivity, errString, Toast.LENGTH_SHORT).show();
                            //指纹认证失败五次会报错，会停留几秒钟后才可以重试
                            cancel.cancel();
//                        if (!mSelfCanceled) {
//                            mFingerCallback.onError(errString.toString());
//                        }
                            if (errorCode == 5) {
                                mFingerCallback.onCancel();
                            } else {
                                mFingerCallback.onError(errorCode, errString.toString());
                            }
                        }

                        @Override
                        public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
                            super.onAuthenticationHelp(helpCode, helpString);
                            //	在认证期间遇到可恢复的错误时调用,比如手指移到太快指纹传感器可能只采集了部分的信息
                            mFingerCallback.onHelp(helpCode, helpString.toString());
                        }

                        @Override
                        public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
                            super.onAuthenticationSucceeded(result);
                            // 当验证的指纹成功时会回调此函数
                            Cipher cipher = result.getCryptoObject().getCipher();
                            if (cipher != null) {
                                try {
                                    /*
                                     * 用于检测三星手机指纹库变化，
                                     * 三星手机指纹库发生变化后前面的initCipher检测不到KeyPermanentlyInvalidatedException
                                     * 但是cipher.doFinal(SECRET_MESSAGE.getBytes())会抛出异常
                                     * 因此以此监听三星手机的指纹库变化
                                     */
                                    //针对三星手机，开启了监听才去检测设备指纹库变化
                                    if (SharePreferenceUtil.isEnableFingerDataChange(mActivity)) {
                                        cipher.doFinal(SECRET_MESSAGE.getBytes());
                                    }
                                    cancel.cancel();
                                    mFingerCallback.onSucceed();
                                    //开启监听设备指纹数据变化
                                    SharePreferenceUtil.saveEnableFingerDataChange(mActivity, true);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    cancel.cancel();
                                    SharePreferenceUtil.saveFingerDataChange(mActivity, true);
                                    mFingerCallback.onChange();
                                }
                            }

                        }

                        @Override
                        public void onAuthenticationFailed() {
                            super.onAuthenticationFailed();
                            // 当指纹验证失败的时候会回调此方法
                            mFingerCallback.onFailed();
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
            mFingerCallback.onFailed();
        }
    }
}
