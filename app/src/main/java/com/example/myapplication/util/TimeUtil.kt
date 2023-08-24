package com.example.myapplication.util

import android.annotation.SuppressLint
import com.airbnb.lottie.L
import com.blankj.utilcode.util.TimeUtils
import java.sql.Time
import java.text.SimpleDateFormat

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.util
 * @ClassName : TimeUtil
 * @Description : 时间工具
 * @Author : yulu
 * @CreateDate : 2023/7/19 10:19
 * @UpdateUser : yulu
 * @UpdateDate : 2023/7/19 10:19
 * @UpdateRemark : 更新说明
 */
object TimeUtil {

    /**
     * 时间日期格式化
     */
    var dateFormatYMDHMS = "yyyy-MM-dd HH:mm:ss"
    var dateFormatYMDHM = "yyyy-MM-dd HH:mm"
    var dateFormatYMD = "yyyy-MM-dd"
    var dateFormatYMDHMSCN = "yyyy年MM月dd日 HH时mm分ss秒"
    var dateFormatYMDHM_CN = "yyyy年MM月dd日 HH时mm分"
    var dateFormatYMD_CN = "yyyy年MM月dd日"

    @SuppressLint("SimpleDateFormat")
    fun getCurrentTime(format: String): String {
        return TimeUtils.getNowString(SimpleDateFormat(format))
    }

    @SuppressLint("SimpleDateFormat")
    fun getCurrentTime(): String {
        return TimeUtils.getNowString(SimpleDateFormat(dateFormatYMDHMS))
    }

    fun getCurrentMill(): Long {
        return TimeUtils.getNowMills()
    }

    fun isToday(mills: Long): Boolean {
        return TimeUtils.isToday(mills)
    }

    @SuppressLint("SimpleDateFormat")
    fun millis2String(mills: Long, dateFormat: String = dateFormatYMDHMS): String {
        return TimeUtils.millis2String(mills, SimpleDateFormat(dateFormat))
    }

    /**
     * 如果小于 1 秒钟内，显示刚刚
     * 如果在 1 分钟内，显示 XXX秒前
     * 如果在 1 小时内，显示 XXX分钟前
     * 如果在 1 小时外的今天内，显示今天15:32
     * 如果是昨天的，显示昨天15:32
     * 其余显示，2016-10-15
     * 时间不合法的情况全部日期和时间信息，如星期六 十月 27 14:21:20 CST 2007
     */
    fun getFriendlyTimeSpanByNow(mills: Long): String {
        return TimeUtils.getFriendlyTimeSpanByNow(mills)
    }

}