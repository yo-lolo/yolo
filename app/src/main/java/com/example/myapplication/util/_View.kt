package com.example.myapplication.util

import android.view.View
import android.widget.TextView

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName :
 * @Package : My Application
 * @ClassName : com.example.myapplication.util
 * @Description : 文件描述
 * @Author : Yulu
 * @CreateDate : 2023/12/11 11:43
 * @UpdateUser : Yulu
 * @UpdateDate : 2023/12/11 11:43
 * @UpdateRemark : 更新说明
 */


/**
 * 点击事件去除重复点击
 */
fun View.onClick(interval: Long, listener: (View) -> Unit) {
    setOnClickListener {
        val time = it.getTag(it.id)?.toString()?.toLongOrNull()
        val currentTime = System.currentTimeMillis()
        if (time == null || currentTime - time > interval) {
            it.setTag(it.id, currentTime)
            listener.invoke(it)
        }
    }
}

/**
 * 点击事件去除重复点击， 默认 500 ms
 */
fun View.onClick(listener: (View) -> Unit) {
    this.onClick(500, listener)
}

private fun View.setVisible(visibility: Int) {
    if (this.visibility != visibility) {
        this.visibility = visibility
    }
}

fun View.visible() {
    setVisible(View.VISIBLE)
}

fun View.invisible() {
    setVisible(View.INVISIBLE)
}

fun View.gone() {
    setVisible(View.GONE)
}

fun View.visibleOrGone(show: Boolean) {
    if (show) visible() else gone()
}

fun TextView.unClick(disable:Boolean) {
    if (disable) {
        alpha = 0.3f
        isEnabled = false
    } else {
        alpha = 1.0f
        isEnabled = true
    }
}

