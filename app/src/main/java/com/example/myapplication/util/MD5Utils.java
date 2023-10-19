package com.example.myapplication.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : simkey-pivot
 * @Package : com.ctq.simkey.app_tools
 * @ClassName : MD5Utils
 * @Description : 文件描述
 * @Author : Abner(zt)
 * @CreateDate : 2022/1/13 10:25
 * @UpdateUser : 更新者
 * @UpdateDate : 2022/1/13 10:25
 * @UpdateRemark : 更新说明
 */
public class MD5Utils {
    public static String getMD5(String string) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] result = md.digest(string.getBytes());
            StringBuffer sb = new StringBuffer();
            for (byte b : result) {
                int sign = b & 0xff;
                String str = Integer.toHexString(sign);
                if (str.length() == 1) {
                    sb.append("0");
                }
                sb.append(str);
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getMD5File(String path) {
        try {
            byte[] buffer = new byte[8192];
            int len = 0;
            MessageDigest md = MessageDigest.getInstance("MD5");
            File f = new File(path);
            FileInputStream fis = new FileInputStream(f);
            while ((len = fis.read(buffer)) != -1) {
                md.update(buffer, 0, len);
            }
            fis.close();
            byte[] b = md.digest();
            return byte2Hex(b);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String byte2Hex(byte[] result){
        StringBuffer sb = new StringBuffer();
        for (byte b : result) {
            int sign = b & 0xff;
            String str = Integer.toHexString(sign);
            if (str.length() == 1) {
                sb.append("0");
            }
            sb.append(str);
        }
        return sb.toString();
    }
}
