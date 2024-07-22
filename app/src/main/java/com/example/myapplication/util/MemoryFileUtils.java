package com.example.myapplication.util;

import android.os.MemoryFile;
import android.os.ParcelFileDescriptor;

import java.io.FileDescriptor;
import java.io.IOException;

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : simkey-pivot
 * @Package : com.ctq.simkey.pivot.utils
 * @ClassName : MemoryFileUtils
 * @Description : 文件描述
 * @Author : Abner(zt)
 * @CreateDate : 2021/11/8 11:41
 * @UpdateUser : 更新者
 * @UpdateDate : 2021/11/8 11:41
 * @UpdateRemark : 更新说明
 */
public class MemoryFileUtils {

    public static ParcelFileDescriptor getParcelFileDescriptor(String memoryFileName, byte[] data) throws IOException {
        /**
         * 创建MemoryFile
         */
        MemoryFile memoryFile = new MemoryFile("client_image", data.length);
        /**
         * 向MemoryFile中写入字节数组
         */
        memoryFile.writeBytes(data, 0, 0, data.length);
        /**
         * 获取MemoryFile对应的FileDescriptor
         */
        FileDescriptor fd = MemoryFileUtils.getFileDescriptor(memoryFile);
        /**
         * 根据FileDescriptor创建ParcelFileDescriptor
         */
        ParcelFileDescriptor pfd = ParcelFileDescriptor.dup(fd);
        return pfd;
    }

    public static FileDescriptor getFileDescriptor(MemoryFile memoryFile) {
        return (FileDescriptor) ReflectUtils.invoke("android.os.MemoryFile",
                memoryFile,
                "getFileDescriptor");
    }


    /**
     * 获取文件大小
     */
    public static String formatFileSize(long fileS) {
        java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "KB";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        return fileSizeString;
    }
}
