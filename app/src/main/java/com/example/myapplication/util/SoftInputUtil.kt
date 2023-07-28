package com.example.myapplication.util

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.util
 * @ClassName : SoftInputUtil
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/7/28 11:50
 * @UpdateUser : yulu
 * @UpdateDate : 2023/7/28 11:50
 * @UpdateRemark : 更新说明
 */
object SoftInputUtil {

    /**
     * 关闭软件盘
     * @param activity
     * @param view 与软键盘相联系的View，这个View要可以获取和失去焦点，一般为EditText
     */
    fun closeSoftInput(activity: Activity, view: View) {
        val imm: InputMethodManager =
            activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0);
    }

    /**
     * 打开软件盘
     * @param activity
     * @param view 与软键盘相联系的View，这个View要可以获取和失去焦点，一般为EditText
     */
    fun openSoftInput(activity: Activity, view: View) {
        val imm: InputMethodManager =
            activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    /**
     * 如果输入法在窗口上已经显示，则隐藏;反之则显示
     * @param activity
     */
    fun toggleSoftInput(activity: Activity) {
        val imm: InputMethodManager =
            activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }


}