/*
 * Created by dengshiwei on 2020/05/11.
 * Copyright 2015－2021 Sensors Data Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.myapplication.util;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;

import com.example.myapplication.base.log.SpeedyLog;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : simkey-sdk-app
 * @Package : com.ctq.simkey.sdk.utils
 * @ClassName : AppInfoUtils
 * @Description : 文件描述
 * @Author : Abner(zt)
 * @CreateDate : 2021/11/8 11:35
 * @UpdateUser : 更新者
 * @UpdateDate : 2021/11/8 11:35
 * @UpdateRemark : 更新说明
 */
public class AppInfoUtils {
    private static String mAppVersionName;
    private static Bundle mConfigBundle;
    public final static String SHA1 = "SHA1";
    /**
     * 获取应用名称
     *
     * @param context Context
     * @return 应用名称
     */
    public static CharSequence getAppName(Context context) {
        if (context == null) return "";
        try {
            PackageManager packageManager = context.getPackageManager();
            ApplicationInfo appInfo = packageManager.getApplicationInfo(context.getPackageName(),
                    PackageManager.GET_META_DATA);
            return appInfo.loadLabel(packageManager);
        } catch (Exception e) {
            SpeedyLog.e(e);
        }
        return "";
    }

    /**
     * 获取 App 的 ApplicationId
     *
     * @param context Context
     * @return ApplicationId
     */
    public static String getProcessName(Context context) {
        if (context == null) return "";
        try {
            return context.getApplicationInfo().processName;
        } catch (Exception ex) {
            SpeedyLog.e(ex);
        }
        return "";
    }

    /**
     * 获取 App 版本名
     *
     * @param context Context
     * @return 获取 App 版本名
     */
    public static String getAppVersionName(Context context) {
        if (context == null) return "";
        if (!TextUtils.isEmpty(mAppVersionName)) {
            return mAppVersionName;
        }
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            mAppVersionName = packageInfo.versionName;
        } catch (Exception e) {
            SpeedyLog.e(e);
        }
        return mAppVersionName;
    }

    /**
     * 获取 App 版本号
     *
     * @param context Context
     * @return App 的版本号
     */
    public static int getVersionCode(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.versionCode;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取主进程的名称
     *
     * @param context Context
     * @return 主进程名称
     */
    public static String getMainProcessName(Context context) {
        if (context == null) {
            return "";
        }
        try {
            return context.getApplicationContext().getApplicationInfo().processName;
        } catch (Exception ex) {
            SpeedyLog.e(ex);
        }
        return "";
    }

    /**
     * 判断当前进程名称是否为主进程
     *
     * @param context Context
     * @param bundle Bundle
     * @return 是否主进程
     */
    public static boolean isMainProcess(Context context, Bundle bundle) {
        if (context == null) {
            return false;
        }

        String mainProcessName = AppInfoUtils.getMainProcessName(context);
        if (TextUtils.isEmpty(mainProcessName) && bundle != null) {
            mainProcessName = bundle.getString("com.sensorsdata.analytics.android.MainProcessName");
        }

        if (TextUtils.isEmpty(mainProcessName)) {
            return true;
        }

        String currentProcess = getCurrentProcessName();
        return TextUtils.isEmpty(currentProcess) || mainProcessName.equals(currentProcess);
    }

    /**
     * 判断线程是否埋点执行线程
     * @return true，埋点执行线程；false，非埋点执行线程
     */
//    public static boolean isTaskExecuteThread() {
//        return TextUtils.equals(ThreadNameConstants.THREAD_TASK_EXECUTE, Thread.currentThread().getName());
//    }

    /**
     * 获取 Application 标签的 Bundle 对象
     * @param context Context
     * @return Bundle
     */
    public static Bundle getAppInfoBundle(Context context) {
        if (mConfigBundle == null) {
            try {
                final ApplicationInfo appInfo = context.getApplicationContext().getPackageManager()
                        .getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
                mConfigBundle = appInfo.metaData;
            } catch (final PackageManager.NameNotFoundException e) {
                SpeedyLog.e(e);
            }
        }

        if (mConfigBundle == null) {
            return new Bundle();
        }
        return mConfigBundle;
    }

    /**
     * 获得当前进程的名字
     *
     * @return 进程名称
     */
    private static String getCurrentProcessName() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                return Application.getProcessName();
            }

            String currentProcess = getCurrentProcessNameByCmd();
            if (TextUtils.isEmpty(currentProcess)) {
                currentProcess = getCurrentProcessNameByAT();
            }
            return currentProcess;
        } catch (Exception e) {
            SpeedyLog.e(e);
        }
        return null;
    }

    private static String getCurrentProcessNameByAT() {
        String processName = null;
        try {
            @SuppressLint("PrivateApi")
            Class<?> activityThread = Class.forName("android.app.ActivityThread", false, Application.class.getClassLoader());
            Method declaredMethod = activityThread.getDeclaredMethod("currentProcessName", (Class<?>[]) new Class[0]);
            declaredMethod.setAccessible(true);
            Object processInvoke = declaredMethod.invoke(null);
            if (processInvoke instanceof String) {
                processName = (String) processInvoke;
            }
        } catch (Throwable e) {
            //ignore
        }
        return processName;
    }

    private static String getCurrentProcessNameByCmd() {
        FileInputStream in = null;
        try {
            String fn = "/proc/self/cmdline";
            in = new FileInputStream(fn);
            byte[] buffer = new byte[256];
            int len = 0;
            int b;
            while ((b = in.read()) > 0 && len < buffer.length) {
                buffer[len++] = (byte) b;
            }
            if (len > 0) {
                return new String(buffer, 0, len, "UTF-8");
            }
        } catch (Throwable e) {
            // ignore
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    SpeedyLog.e(e);
                }
            }
        }
        return null;
    }

    /**
     * 返回一个签名的对应类型的字符串
     *
     * @param context
     * @param packageName
     * @param type
     *
     * @return
     */
    public static String getSingInfo(Context context, String packageName, String type) {
        String tmp = null;
        Signature[] signs = getSignatures(context, packageName);
        for (Signature sig : signs) {
            if (SHA1.equals(type)) {
                tmp = getSignatureString(sig, SHA1);
                break;
            }
        }
        return tmp;
    }

    /**
     * 返回对应包的签名信息
     *
     * @param context
     * @param packageName
     *
     * @return
     */
    public static Signature[] getSignatures(Context context, String packageName) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
            return packageInfo.signatures;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取相应的类型的字符串（把签名的byte[]信息转换成16进制）
     *
     * @param sig
     * @param type
     *
     * @return
     */
    public static String getSignatureString(Signature sig, String type) {
        byte[] hexBytes = sig.toByteArray();
        String fingerprint = "error!";
        try {
            MessageDigest digest = MessageDigest.getInstance(type);
            if (digest != null) {
                byte[] digestBytes = digest.digest(hexBytes);
                StringBuilder sb = new StringBuilder();
                for (byte digestByte : digestBytes) {
                    sb.append((Integer.toHexString((digestByte & 0xFF) | 0x100)).substring(1, 3));
                }
                fingerprint = sb.toString();
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return fingerprint;
    }
}
