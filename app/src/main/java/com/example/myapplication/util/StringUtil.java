package com.example.myapplication.util;

import android.text.TextUtils;

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : simkey-pivot
 * @Package : com.ctq.simkey.app_tools
 * @ClassName : StringUtil
 * @Description : 文件描述
 * @Author : Abner(zt)
 * @CreateDate : 2022/2/16 10:36
 * @UpdateUser : 更新者
 * @UpdateDate : 2022/2/16 10:36
 * @UpdateRemark : 更新说明
 */
public class StringUtil {

    /**
     * 字母映射表
     */
    private static final char[] HEX_DIGITS =
            {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    /**
     * 字节数组转16进制字符串
     *
     * @param bytes 字节数组
     * @return 获取的字符串
     */
    public static String bytes2HexString(final byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        int len = bytes.length;
        if (len <= 0) {
            return null;
        }
        char[] ret = new char[len << 1];
        for (int i = 0, j = 0; i < len; i++) {
            ret[j++] = HEX_DIGITS[bytes[i] >> 4 & 0x0f];
            ret[j++] = HEX_DIGITS[bytes[i] & 0x0f];
        }
        return new String(ret);
    }

    /**
     * 字节数组转16进制字符串
     */
    public static String byteArrayToHexString(byte[] data, int pos, int len) {
        StringBuilder sb = new StringBuilder(data.length * 2);
        for (int i = pos; i < len; i++) {
            int v = data[i] & 0xff;
            if (v < 16) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(v));
        }
        return sb.toString().toUpperCase();
    }

    /**
     * 16进制字符串转字节数组
     *
     * @param hexString 字符串
     * @return 获取的字节数组
     */
    public static byte[] hexString2Bytes(String hexString) {
        if (isSpace(hexString)) {
            return null;
        }
        int len = hexString.length();
        if (len % 2 != 0) {
            hexString = "0" + hexString;
            len = len + 1;
        }
        char[] hexBytes = hexString.toUpperCase().toCharArray();
        byte[] ret = new byte[len >> 1];
        for (int i = 0; i < len; i += 2) {
            ret[i >> 1] = (byte) (hex2Dec(hexBytes[i]) << 4 | hex2Dec(hexBytes[i + 1]));
        }
        return ret;
    }

    private static boolean isSpace(final String s) {
        if (s == null) {
            return true;
        }
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private static int hex2Dec(final char hexChar) {
        if (hexChar >= '0' && hexChar <= '9') {
            return hexChar - '0';
        } else if (hexChar >= 'A' && hexChar <= 'F') {
            return hexChar - 'A' + 10;
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * 设置手机号中间四位不可见
     */
    public static String hidePhone(String phone) {
        if (!TextUtils.isEmpty(phone) && phone.length() == 11) {
            phone = phone.substring(0, 3) + "****" + phone.substring(7, 11);
        }
        return phone;
    }

    /**
     * 获取姓名后两位
     */
    public static String getNickName(String name) {
        if (TextUtils.isEmpty(name) || name.length() < 2) {
            return name;
        }
        name = name.substring(name.length() - 2);
        return name;
    }

    /**
     * 移除证书首尾和换行符
     * -----BEGIN CERTIFICATE REQUEST-----
     * -----END CERTIFICATE REQUEST-----
     */
    public static String removeCertHeadAndTail(String cert) {
        if (!TextUtils.isEmpty(cert)
                && cert.contains("-----BEGIN CERTIFICATE REQUEST-----")
                && cert.contains("-----END CERTIFICATE REQUEST-----")) {
            cert = cert
                    .replace("-----BEGIN CERTIFICATE REQUEST-----", "")
                    .replace("-----END CERTIFICATE REQUEST-----", "")
                    .replaceAll("\n", "");
        }
        return cert;
    }

    /**
     * 数字证书截取证书有效期开始日期结束日期
     */
    public static String[] getCertDate(String target) {
        String[] result = new String[2];
        String[] data = target.split("\n");
        if (data != null) {
            if (data.length > 1 && data[1].contains("from:")) {
                result[0] = data[1].replace("from:", "");
                result[0] = DateUtil.dayAdd8(result[0]);
            }
            if (data.length > 2 && data[2].contains("to:")) {
                result[1] = data[2].replace("to:", "");
                result[1] = DateUtil.dayAdd8(result[1]);
            }
        }
        return result;
    }

    /**
     * 正则表达式验证手机号格式
     */
    public static boolean isValidPhoneNumber(String phoneNumber) {
        // 正则表达式验证手机号格式
        String regex = "^1[3456789]\\d{9}$";
        return phoneNumber.matches(regex);
    }
}