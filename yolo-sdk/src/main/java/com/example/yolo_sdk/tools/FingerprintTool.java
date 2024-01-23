package com.example.yolo_sdk.tools;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.yolo_sdk.biometric.FingerManager;
import com.example.yolo_sdk.biometric.FingerprintCallback;
import com.example.yolo_sdk.biometric.IFingerCallback;
import com.example.yolo_sdk.biometric.SharePreferenceUtil;
import com.example.yolo_sdk.biometric.SimpleFingerCallback;

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : simkey-sdk-app
 * @Package : com.ctq.simkey.sdk.tools
 * @ClassName : FingerprintTool
 * @Description : 文件描述
 * @Author : Abner(zt)
 * @CreateDate : 2022/7/21 16:11
 * @UpdateUser : 更新者
 * @UpdateDate : 2022/7/21 16:11
 * @UpdateRemark : 更新说明
 */

public class FingerprintTool {
    private static final String TAG = FingerprintTool.class.getSimpleName();
    private static volatile FingerprintTool sInstance = null;

    private Context mContext;

    public static FingerprintTool getInstance(Context mContext) {
        if (sInstance == null) {
            synchronized (FingerprintTool.class) {
                if (sInstance == null) {
                    sInstance = new FingerprintTool(mContext);
                }
            }
        }
        return sInstance;
    }

    private FingerprintTool(Context mContext) {
        this.mContext = mContext;
        initFingerprint();
    }


    public void initFingerprint() {
        FingerManager.SupportResult fingerSupport = FingerManager.checkSupport(mContext);
        switch (fingerSupport) {
            case DEVICE_UNSUPPORTED:
                //设备不支持指纹
                SharePreferenceUtil.setFingerSupportState(mContext, 1);
                break;
            case SUPPORT_WITHOUT_DATA:
                //没有录入指纹
                SharePreferenceUtil.setFingerSupportState(mContext, 2);
                break;
            case SUPPORT:
                SharePreferenceUtil.setFingerSupportState(mContext, 0);
                break;
            default:
        }
    }

    /**
     * 同步指纹数据
     */
    public void updateFingerData() {
        FingerManager.updateFingerData(mContext);
    }

    public int checkFinger(AppCompatActivity activity, FingerprintCallback callback) {
        if (SharePreferenceUtil.getFingerSupportState(mContext) == 0) {
            updateFingerData();
            FingerManager.build().setApplication(activity.getApplication())
                    .setTitle("指纹验证")
                    .setDes("请按下指纹")
                    .setNegativeText("取消")
                    .setFingerCallback(new SimpleFingerCallback() {
                        @Override
                        public void onError(int code, String error) {
                            Log.i(TAG, "指纹识别错误code-->" + code + "-error--" + error);
                            callback.onFingerCheckCallback("指纹识别错误code-->" + code + "-error--" + error);
                        }

                        @Override
                        public void onHelp(int code, String help) {
                        }

                        @Override
                        public void onSucceed() {
                            //验证成功
                            Log.i(TAG, "指纹验证成功");
                            callback.onFingerCheckCallback("指纹验证成功");

                        }

                        @Override
                        public void onFailed() {
                            //指纹无法识别
                            Log.i(TAG, "指纹无法识别");
                            callback.onFingerCheckCallback("指纹无法识别");
                        }

                        @Override
                        public void onCancel() {
                            Log.i(TAG, "指纹取消");
                            callback.onFingerCheckCallback("指纹取消");
                        }

                        @Override
                        public void onChange() {
                            //指纹数据发生了变化
                            Log.i(TAG, "指纹数据发生了变化");
                            callback.onFingerCheckCallback("指纹数据发生了变化");
                        }
                    })
                    .create()
                    .startListener(activity);
        }
        return SharePreferenceUtil.getFingerSupportState(mContext);
    }

    public int checkFingerSupport() {
        return SharePreferenceUtil.getFingerSupportState(mContext);
    }

    public boolean hasChangeFingerData() {
        if (FingerManager.hasFingerprintChang(mContext)) {
            return true;
        } else {
            return false;
        }
    }

}
