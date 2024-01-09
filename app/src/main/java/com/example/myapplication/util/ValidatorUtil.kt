package com.example.myapplication.util

import java.util.regex.Pattern

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName :
 * @Package : My Application
 * @ClassName : com.example.myapplication.util
 * @Description : 文件描述
 * @Author : Yulu
 * @CreateDate : 2024/1/8 17:50
 * @UpdateUser : Yulu
 * @UpdateDate : 2024/1/8 17:50
 * @UpdateRemark : 更新说明
 */
object ValidatorUtil {
    /**
     * 正则表达式：验证手机号
     */
    private const val REGEX_MOBILE = "^[1]\\d{10}$"

    /**
     * 校验手机号
     *
     * @param mobile
     * @return 校验通过返回true，否则返回false
     */
    fun isMobile(mobile: String): Boolean {
        return Pattern.matches(REGEX_MOBILE, mobile)
    }
}