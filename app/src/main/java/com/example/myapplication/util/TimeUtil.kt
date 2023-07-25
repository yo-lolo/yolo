package com.example.myapplication.util

import android.annotation.SuppressLint
import com.blankj.utilcode.util.TimeUtils
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
    var dateFormatYMD = "yyyy-MM-dd"
    var dateFormatYMDHMSCN = "yyyy年MM月dd日 HH时mm分ss秒"
    var dateFormatYMDCN = "yyyy年MM月dd日"

    @SuppressLint("SimpleDateFormat")
    fun getCurrentTime(format: String): String {
        return TimeUtils.getNowString(SimpleDateFormat(format))
    }

    @SuppressLint("SimpleDateFormat")
    fun getCurrentTime(): String {
        return TimeUtils.getNowString(SimpleDateFormat(dateFormatYMDHMS))
    }

}