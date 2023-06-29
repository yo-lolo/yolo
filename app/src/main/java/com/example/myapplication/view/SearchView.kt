package com.example.myapplication.view

import com.example.myapplication.R
import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.EditText
import android.widget.FrameLayout

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : safe-phone
 * @Package : com.ctq.sphone.market.view
 * @ClassName : 类名
 * @Description : 文件描述
 * @Author : hulifei
 * @CreateDate : 2023/1/31 10:52
 * @UpdateUser : hulifei
 * @UpdateDate : 2023/1/31 10:52
 * @UpdateRemark : 更新说明
 */
@SuppressLint("ClickableViewAccessibility")
class SearchView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    var searchEditText: EditText
    var searchButtonListener: ((String) -> Unit)? = null

    init {
        inflate(context, R.layout.layout_search_view, this)

        searchEditText = findViewById(R.id.search_edit)
        //设置点击查询事件
        searchEditText.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                if (searchEditText.compoundDrawables[2] != null) {
                    val start =
                        searchEditText.width - searchEditText.totalPaddingRight - searchEditText.paddingRight
                    val end = searchEditText.width
                    val avaliable = (event.x > start) && (event.y < end)
                    if (avaliable && searchButtonListener != null) {
                        val text = searchEditText.text.toString()
                        if (text.isNotBlank()) {
                            searchButtonListener?.invoke(text)
                        }

                    }
                }
            }
            false
        }
    }

}