package com.example.myapplication.view

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.example.myapplication.R

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.view
 * @ClassName : SearchStubView
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/6/29 16:00
 * @UpdateUser : yulu
 * @UpdateDate : 2023/6/29 16:00
 * @UpdateRemark : 更新说明
 */
class SearchStubView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    init {
        inflate(context, R.layout.layout_search_stub, this)
    }
}