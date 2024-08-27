package com.example.myapplication.util;

import static android.telephony.gsm.SmsMessage.ENCODING_16BIT;
import static android.telephony.gsm.SmsMessage.ENCODING_7BIT;
import static android.telephony.gsm.SmsMessage.ENCODING_UNKNOWN;
import static android.telephony.gsm.SmsMessage.MAX_USER_DATA_BYTES;
import static android.telephony.gsm.SmsMessage.MAX_USER_DATA_SEPTETS;
import static com.example.myapplication.log.SpeedyLog.TAG;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.telephony.PhoneNumberUtils;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Config;
import android.util.Log;

import com.example.myapplication.log.SpeedyLog;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class SimSmsUtil {

    void test() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byteArrayOutputStream.write(0x00);
        byteArrayOutputStream.write(0x00);
        byteArrayOutputStream.write(0x00);
        byteArrayOutputStream.write(0x00);
        byte[] timeStamp = getTimeStamp();
        for (int i = 0; i < timeStamp.length; i++) {
            byteArrayOutputStream.write(timeStamp[i]);
        }
    }

    /***
     * 1)	byte[] smsc : 短信服务中心的地址，个人认为在复制到SIM卡过程中可以为空。
     * 2)	byte[] pdu : 中文翻译是协议数据单元，这个参数最为重要，一会我们会做详细地解释说明。
     * 3)	int status : 短信存储在Icc卡上的状态，有4种状态，1是已读，3是未读，5是已发送，7是未发送。
     * @param smsc
     * @param pdu
     * @param status
     */
    public boolean writeSMStoIcc(byte[] smsc, byte[] pdu, int status) {
        SpeedyLog.d(TAG, "writeSMStoIcc()");
//		mKeyboardHelper = new ReflectionInternal(this,"android.telephony.SmsManager");
        //调用类，声明类，mKeyboardView，mPasswordEntry，为需要传递的参数
//		mKeyboardHelper.setInt("copyMessageToIcc", 0);
        boolean flag = false;
        SmsManager newSmsManager = SmsManager.getDefault();
        try {
            Class smsManagerClass = Class.forName("android.telephony.SmsManager");
            Method localMethod = smsManagerClass.getMethod("copyMessageToIcc", new Class[]{byte[].class, byte[].class, Integer.TYPE});
            Object[] arrayList = new Object[3];
            arrayList[0] = smsc;
            arrayList[1] = pdu;
            arrayList[2] = status;
            try {
                flag = ((Boolean) localMethod.invoke(newSmsManager, arrayList)).booleanValue();
            } catch (IllegalAccessException e) {
                SpeedyLog.d(TAG, e.getMessage());

            } catch (IllegalArgumentException e) {
                SpeedyLog.d(TAG, e.getMessage());

            } catch (InvocationTargetException e) {
                SpeedyLog.d(TAG, e.getMessage());
            }
        } catch (NoSuchMethodException e) {
            SpeedyLog.d(TAG, "NoSuchMethodException :" + e.getMessage());
        } catch (ClassNotFoundException e) {
            SpeedyLog.d(TAG, "ClassNotFoundException :" + e.getMessage());
        }
        return flag;
    }

    /**
     * 读sim卡短信
     *
     * @return
     */
    public ArrayList<SmsMessage> getSmsList() {
        SpeedyLog.d(TAG, "getSmsList()");
        ArrayList<SmsMessage> list = new ArrayList<SmsMessage>();
        SmsManager newSmsManager = SmsManager.getDefault();
        try {
            Class<?> smsManagerClass = Class.forName("android.telephony.SmsManager");
            Method localMethod = smsManagerClass.getMethod("getAllMessagesFromIcc", null);
            try {
                list = (ArrayList<SmsMessage>) localMethod.invoke(newSmsManager, null);
            } catch (IllegalAccessException e) {
                SpeedyLog.d(TAG, e.getMessage());
            } catch (IllegalArgumentException e) {
                SpeedyLog.d(TAG, e.getMessage());
            } catch (InvocationTargetException e) {
                SpeedyLog.d(TAG, e.getMessage());
            }
        } catch (NoSuchMethodException e) {
            SpeedyLog.d(TAG, "NoSuchMethodException :" + e.getMessage());
        } catch (ClassNotFoundException e) {
            SpeedyLog.d(TAG, "ClassNotFoundException :" + e.getMessage());
        }

        return list;
    }

    /***
     * 获取sim卡上联系人列表
     * @return
     */
    public List<SimContacts> getSimContactsList(Activity act) {
        SpeedyLog.d(TAG, "getSimContactsList()");
        Cursor mCursor = null;
        List<SimContacts> list = new ArrayList<SimContacts>();
        try {
            mCursor = act.getContentResolver().query(Uri.parse("content://icc/adn"), null, null, null, null);
            if (mCursor != null) {
                while (mCursor.moveToNext()) {
                    SimContacts simContacts = new SimContacts();
                    int nameFieldColumnIndex = mCursor.getColumnIndex("name");
                    if (nameFieldColumnIndex >= 0) {
                        simContacts.setName(mCursor.getString(nameFieldColumnIndex));
                    }

                    int numberFieldColumnIndex = mCursor.getColumnIndex("number");
                    if (numberFieldColumnIndex >= 0) {
                        simContacts.setNumber(mCursor.getString(numberFieldColumnIndex));
                    }

                    int emailsFieldColumnIndex = mCursor.getColumnIndex("emails");
                    if (emailsFieldColumnIndex >= 0) {
                        simContacts.setEmails(mCursor.getString(emailsFieldColumnIndex));
                    }


                    int idFieldColumnIndex = mCursor.getColumnIndex("_id");
                    if (idFieldColumnIndex >= 0) {
                        simContacts.set_id(mCursor.getString(idFieldColumnIndex));
                    }
                    list.add(simContacts);
                }
            }
        } catch (Exception e) {
            SpeedyLog.d(TAG, e.getMessage());
        } finally {
            if (mCursor != null) {
                mCursor.close();
            }
        }
        return list;
    }

    /**
     * 根据id 删除对应记录
     *
     * @param act
     * @param id
     */
    public void deleteSimContactById(Activity act, String id) {
        SpeedyLog.d(TAG, "deleteSimContactById()");
        if (id != null) {
            Cursor cursor = null;
            String whereCon = "_id=" + id + "";
            try {
                act.getContentResolver().delete(Uri.parse("content://icc/adn"), whereCon, null);
            } catch (Exception e) {
                SpeedyLog.d(TAG, e.getMessage());
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
    }

    /**
     * 根据用户名和电话号码定位联系人并删除
     *
     * @param act
     * @param name
     * @param phone
     * @return
     */
    public boolean deleteSimContactByNameAndPhone(Activity act, String name, String phone) {
        SpeedyLog.d(TAG, "deleteSimContactByNameAndPhone()");
        Cursor cursor = null;
        int flagInt = 0;
        try {
            String where = "tag='" + name + "'";
            where += " AND number='" + phone + "'";
            flagInt = act.getContentResolver().delete(Uri.parse("content://icc/adn"), where, null);
        } catch (Exception e) {
            SpeedyLog.d(TAG, e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        if (flagInt > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 插入联系人
     *
     * @param act
     * @param list
     */
    public void simInsertByList(Activity act, List<SimContacts> list) {
        SpeedyLog.d(TAG, "simInsertByList()");
        for (SimContacts simContacts : list) {
            ContentValues values = new ContentValues();
            values.put("tag", simContacts.getName());
            values.put("number", simContacts.getNumber());
            Uri uri = null;
            try {
                uri = act.getContentResolver().insert(Uri.parse("content://icc/adn"), values);
            } catch (Exception e) {
                SpeedyLog.d(TAG, e.getMessage());
            }
            if (uri != null) {
                uri.toString();
            }
        }
    }

//    private android.telephony.gsm.SmsMessage.SubmitPdu getSubmitPdu(String scAddress, String destinationAddress, String message,
//                                                                    boolean statusReportRequested, byte[] header, int encoding) {
//
//        // Perform null parameter checks.
//        if (message == null || destinationAddress == null) {
//            return null;
//        }
//
//        android.telephony.gsm.SmsMessage.SubmitPdu ret = new android.telephony.gsm.SmsMessage.SubmitPdu();
//        // MTI = SMS-SUBMIT, UDHI = header != null
//        //在这个方法中获得了数据编码方案(DCS,就是例子中的0b)前的所有字节。
//        ByteArrayOutputStream bo = getSubmitPduHead(scAddress, destinationAddress,
//                statusReportRequested, ret);
//        // User Data (and length)
//        byte[] userData;
//        if (encoding == ENCODING_UNKNOWN) {
//            // First, try encoding it with the GSM alphabet
//            encoding = ENCODING_7BIT;
//        }
//        try {
//            if (encoding == ENCODING_7BIT) {
//                userData = GsmAlphabet.stringToGsm7BitPackedWithHeader(message, header);
//            } else { // assume UCS-2
//                try {
//                    userData = encodeUCS2(message, header);
//                } catch (UnsupportedEncodingException uex) {
//                    Log.e("GSM", "Implausible UnsupportedEncodingException ", uex);
//                    return null;
//                }
//            }
//        } catch (EncodeException ex) {
//            // Encoding to the 7-bit alphabet failed. Let's see if we can
//            // send it as a UCS-2 encoded message
//            try {
//                userData = encodeUCS2(message, header);
//                encoding = ENCODING_16BIT;
//            } catch (UnsupportedEncodingException uex) {
//                Log.e("GSM", "Implausible UnsupportedEncodingException ", uex);
//                return null;
//            }
//        }
//
//        if (encoding == ENCODING_7BIT) {
//            if ((0xff & userData[0]) > MAX_USER_DATA_SEPTETS) {
//                // Message too long
//                return null;
//            }
//            bo.write(0x00);
//        } else { // assume UCS-2
//            if ((0xff & userData[0]) > MAX_USER_DATA_BYTES) {
//                // Message too long
//                return null;
//            }
//            // TP-Data-Coding-Scheme
//            // Class 3, UCS-2 encoding, uncompressed
//            bo.write(0x0b); //DCS，数据编码方案
//        }
//        //获得所需的时间戳，否则会显示乱码
//        byte[] timeStamp = getTimeStamp();
//        for (int i = 0; i < timeStamp.length; i++) {
//            bo.write(timeStamp[i]);
//        }
//        bo.write(0x23);
//        //写入短信信息，含有中文时会自动捕获异常并改变其编码格式
//        bo.write(userData, 0, userData.length);
//        ret.encodedMessage = bo.toByteArray();
//        return ret;
//    }
//
//    private static ByteArrayOutputStream getSubmitPduHead(
//            String scAddress, String destinationAddress,
//            boolean statusReportRequested, android.telephony.gsm.SmsMessage.SubmitPdu ret) {
//        ByteArrayOutputStream bo = new ByteArrayOutputStream(
//                MAX_USER_DATA_BYTES + 40);
//        // SMSC address with length octet, or 0
//        if (scAddress == null) {
//            ret.encodedScAddress = null;
//        } else {
//            ret.encodedScAddress = PhoneNumberUtils.networkPortionToCalledPartyBCDWithLength(
//                    scAddress);
//        }
//        // TP-Message-Type-Indicator (and friends)
//        if (statusReportRequested) {
//            // Set TP-Status-Report-Request bit.
//            mtiByte |= 0x20;
//            if (Config.LOGD) Log.d("GSM", "SMS status report requested");
//        }
//
////bo.write(0);
////bo.write(mtiByte);
//
//        if (null != ret.encodedScAddress) {
//            for (int i = 0, len = ret.encodedScAddress.length; i < len; i++) {
//                bo.write(ret.encodedScAddress[i]);
//            }
//        }
//
//// space for TP-Message-Reference
//        bo.write(0);
//
//        byte[] daBytes;
//
//        daBytes = PhoneNumberUtils.networkPortionToCalledPartyBCD(destinationAddress);
//
//// destination address length in BCD digits, ignoring TON byte and pad
//// TODO Should be better.
//        bo.write((daBytes.length - 1) * 2
//                - ((daBytes[daBytes.length - 1] & 0xf0) == 0xf0 ? 1 : 0));
//
//// destination address
//        bo.write(daBytes, 0, daBytes.length);
//
//// TP-Protocol-Identifier
//        bo.write(0);
//        return bo;
//    }

    private byte[] encodeUCS2(String message, byte[] header)
            throws UnsupportedEncodingException {
        byte[] userData, textPart;
        textPart = message.getBytes("utf-16be");

        if (header != null) {
// Need 1 byte for UDHL
            userData = new byte[header.length + textPart.length + 1];

            userData[0] = (byte) header.length;
            System.arraycopy(header, 0, userData, 1, header.length);
            System.arraycopy(textPart, 0, userData, header.length + 1, textPart.length);
        } else {
            userData = textPart;
        }
        byte[] ret = new byte[userData.length + 1];
        ret[0] = (byte) (userData.length & 0xff);
        System.arraycopy(userData, 0, ret, 1, userData.length);
        return ret;
    }

    private byte[] getTimeStamp() {
        long time = System.currentTimeMillis();
        byte[] timeStamp = null;
        StringBuilder builder = new StringBuilder();
        SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd-hh-mm-ss");
        String[] array = sdf.format(time).split("-");
        for (int i = 0; i < array.length; i++) {
            int p = Integer.parseInt(array[i]);
            int q = p / 10 + 10 * (p % 10);
            builder = q < 10 ? builder.append("0" + q) : builder.append(q);
        }
        timeStamp = StringUtil.hexString2Bytes(builder.toString());

        return timeStamp;
    }

}

