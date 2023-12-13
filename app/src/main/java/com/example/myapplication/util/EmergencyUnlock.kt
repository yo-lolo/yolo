package com.example.myapplication.util

import android.text.TextUtils
import android.util.Log
import com.blankj.utilcode.util.TimeUtils
import com.blankj.utilcode.util.ToastUtils
import com.example.myapplication.getTag
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : safe-phone
 * @Package : com.ctq.sphone.market.util
 * @ClassName : EmergencyUnlock
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/8/4 16:04
 * @UpdateUser : yulu
 * @UpdateDate : 2023/8/4 16:04
 * @UpdateRemark : 更新说明
 */

/**
 * 针对系统异常导致终端进入保护模式且后台无法远程下发模式切换的场景，系统预留应急解锁功能，通过此功能使终端进入生活模式可正常使用。
 */
object EmergencyUnlock {
    private val TAG = getTag()

    /**
     * 校验数字口令
     * 将数字口令存储到数据库中
     * 校验成功则切换到生活模式
     * 口令只使用一次不可重复使用
     */
    fun checkDigest(data: String) {
            if (TextUtils.equals(data, generateNumericPass())) {
                //验证成功，缓存该数字口令
            } else {
                ToastUtils.showShort("验证失败，请重试")
            }

    }

    /**
     * 通过imei、IccId和时间"yyyy-MM-dd HH:mm" 生成数字口令
     *
     */
    private fun generateNumericPass(): String {
        val iccId = "89860323650100247595"
        val imei = "861259069000339"
        Log.e(TAG, "iccId:$iccId \nimei:$imei")
        val dateStr = getHourStamp().toString()
        Log.e(TAG, "取模日期时间戳:$dateStr")
        val str = StringBuffer().append(iccId).append(imei).append(dateStr).toString()
        Log.e(TAG, "待进行md5加密的字符串:$str")
        val digitalPassword = digitalPassword(str)
        Log.e(TAG, "加密后生成的6位数字口令:$digitalPassword")
        return digitalPassword
    }

    /**
     * 通过Md5生成6位的数字口令
     * 1.对字符串进行md5加密生成hashcode 2.是用&Integer.MAX_VALUE防止生成负数 3.截取数字前六位
     * @param str
     * @return
     */
    private fun digitalPassword(str: String): String {
        return java.lang.String.valueOf(MD5Utils.getMD5(str).hashCode() and Int.MAX_VALUE)
            .substring(0, 6)
    }

    /**
     * 获取当前时间在当前的第几小时的时间戳
     * 用当前时间的下一个小时整的时间戳
     * 如果是15:57分,取17:00,防止最后三分钟生成口令
     * @return 时间戳
     */
    private fun getHourStamp(): Long {
        val hourStamp: Long
        val calendar = Calendar.getInstance()
        calendar.time = Date()
        Log.e(TAG, "当前系统时间:${TimeUtils.millis2String(Calendar.getInstance().timeInMillis)}")
        calendar[Calendar.SECOND] = calendar[Calendar.SECOND] + 180
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.time)
        hourStamp = try {
            val parse = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(format)
            Log.e(TAG, "字符串格式下的时间：${TimeUtils.millis2String(parse.time)}")
            parse.time
        } catch (e: ParseException) {
            throw java.lang.RuntimeException("日期转换时间戳失败", e)
        }
        Log.e(TAG, "当前日期时间戳:$hourStamp")
        Log.e(TAG, "当前数字口令有效时间还剩:${180 - (hourStamp % (3 * 60 * 1000) / 1000)}s")
        return hourStamp / (3 * 60 * 1000)
    }


}