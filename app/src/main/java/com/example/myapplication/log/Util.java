package com.example.myapplication.log;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : simkey-pivot
 * @Package : 包名
 * @ClassName : 类名
 * @Description : 文件描述
 * @Author : Herb(lhb)
 * @CreateDate : 2023/08/17 17:14
 * @UpdateUser : 更新者
 * @UpdateDate : 2023/08/17 17:14
 * @UpdateRemark : 更新说明
 */
public class Util {

    private static ThreadLocal<SimpleDateFormat> threadLocal = new ThreadLocal<>();

    @SuppressLint("SimpleDateFormat")
    public static String formatTime() {//new SimpleDateFormat这个东西太费性能了,ThreadLocal优化下
        SimpleDateFormat sdf = threadLocal.get();
        if (sdf == null) {
            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            threadLocal.set(sdf);
        }
        return sdf.format(new Date());
    }

    public static boolean writeData(WriteData writeData, String folder, String fileName, byte[] bytes) {

        try {
            if (writeData != null && writeData.writeData(folder, fileName, bytes)) {
                return true;
            }

            File file = new File(folder);
            if (!file.exists()) file.mkdirs();
//            PrintWriter pw = new PrintWriter(file);
//            FileOutputStream fos = new FileOutputStream(new File(file, fileName));
//            OutputStreamWriter osw = new OutputStreamWriter(fos);
//            fos.write(bytes);
//            fos.close();
            // 打开一个随机访问文件流，按读写方式
            RandomAccessFile randomFile = new RandomAccessFile(new File(file, fileName), "rw");
            randomFile.seek(randomFile.length());
            randomFile.write(bytes);
            randomFile.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    private static String getSimpleClassName(String name) {
        int lastIndex = name.lastIndexOf(".");
        return name.substring(lastIndex + 1);
    }


    public static String getStack(int methodCount) {
        StackTraceElement[] trace = Thread.currentThread().getStackTrace();//[0][1]系统方法 [2]本方法

        StringBuilder builder = new StringBuilder();

        for (int i = 3; i < trace.length; i++) {
            StackTraceElement element = trace[i];
            String name = getSimpleClassName(element.getClassName());
            if (name.startsWith(SpeedyLog.class.getSimpleName()))
                continue;
            if (methodCount == 0) break;
//            if (builder.length() > 0)
            builder.append("\n");
            builder.append(name)
                    .append(".")
                    .append(element.getMethodName())
                    .append("(")
                    .append(element.getFileName())
                    .append(":")
                    .append(element.getLineNumber())
                    .append(")");
            methodCount--;
        }
        return builder.toString();
    }

    /**
     * 检测日志删除过期的
     */
    public static void checkLog(final SpeedyLogConfig speedyLogConfig) {
        if (speedyLogConfig.day() <= 0) return;

        File f = new File(speedyLogConfig.path());
        if (!f.isDirectory()) return;
        File[] files = f.listFiles();
        if (files == null) return;

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -speedyLogConfig.day() + 1);
        String date = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());

        for (File file : files) {
            if (file.isDirectory()) continue;
            String name = file.getName();
            if (name.endsWith(".log") && name.compareTo(date) < 0) {
                Log.i(SpeedyLog.TAG, "Del log:" + file.getName());
                file.delete();
            }
        }
    }

    /**
     * 收集手机信息
     */
    public static String dumpPhoneInfo(Context context) {
        StringBuilder pw = new StringBuilder("\n");

        try {
            //包信息
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = null;
            pi = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
            pw.append("App Version:");
            pw.append(pi.versionName);
            pw.append("(");
            pw.append(pi.versionCode);
            pw.append(")  ");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Android版本号
        pw.append("OS Version:");
        pw.append(Build.VERSION.RELEASE);
        pw.append("(");
        pw.append(Build.VERSION.SDK_INT);
        pw.append(")  ");

        //手机制造商
        pw.append("Vendor:");
        pw.append(Build.MODEL).append("(").append(Build.MANUFACTURER).append(")");
        pw.append("  ");

        //cpu架构
        pw.append("CPU ABI:");
        if (Build.VERSION.SDK_INT >= 21 && Build.SUPPORTED_ABIS != null) {
            for (String ss : Build.SUPPORTED_ABIS)
                pw.append(ss).append(" ");
        } else
            pw.append(Build.CPU_ABI);
        return pw.toString();
    }


}
