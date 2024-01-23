package com.example.yolo_sdk.biometric;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : simkey-sdk-app
 * @Package : com.ctq.simkey.sdk.func.biometric
 * @ClassName : SharePreferenceUtil
 * @Description : 文件描述
 * @Author : Abner(zt)
 * @CreateDate : 2022/7/19 9:13
 * @UpdateUser : 更新者
 * @UpdateDate : 2022/7/19 9:13
 * @UpdateRemark : 更新说明
 */
public class SharePreferenceUtil {
    private static final String DEFAULT_NAME = "finger";
    /*
     * 是否启用指纹变化监听
     * 如果还没有开启指纹数据变化监听，但是指纹数据已经发生了改变，这种情况需要排除，清除指纹数据变化，重新生成指纹加密库key
     */
    private static String KEY_IS_FINGER_CHANGE_ENABLE = "is_finger_change_enable";
    private static String KEY_IS_FINGER_CHANGE = "is_finger_change";//指纹是否变化了
    private static String FINGER_SUPPORT_STATE = "finger_support_state";//指纹是否变化了

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(DEFAULT_NAME, Context.MODE_PRIVATE);
    }

    //保存是否启动监听设备指纹数据变化的状态
    public static void saveEnableFingerDataChange(Context context, Boolean value) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putBoolean(KEY_IS_FINGER_CHANGE_ENABLE, value);
        editor.apply();
    }

    //判断是否启动了监听设备指纹数据变化
    public static boolean isEnableFingerDataChange(Context context) {
        return getSharedPreferences(context).getBoolean(KEY_IS_FINGER_CHANGE_ENABLE, false);
    }

    public static void saveFingerDataChange(Context context, Boolean value) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putBoolean(KEY_IS_FINGER_CHANGE, value);
        editor.apply();
    }

    public static boolean isFingerDataChange(Context context) {
        return getSharedPreferences(context).getBoolean(KEY_IS_FINGER_CHANGE, false);
    }

    public static void setFingerSupportState(Context context, int state) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putInt(FINGER_SUPPORT_STATE, state);
        editor.apply();
    }

    public static int getFingerSupportState(Context context) {
        return getSharedPreferences(context).getInt(FINGER_SUPPORT_STATE, 3);
    }


}
