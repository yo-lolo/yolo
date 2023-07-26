package com.example.myapplication.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.myapplication.R

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.view
 * @ClassName : HeadLayout
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/6/29 13:47
 * @UpdateUser : yulu
 * @UpdateDate : 2023/6/29 13:47
 * @UpdateRemark : 更新说明
 */
class HeadLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {

    //左边容器
    private var leftContainer: FrameLayout

    //中间容器
    private var centerContainer: FrameLayout

    //右边容器
    private var rightContainer: FrameLayout

    private var headLinear: LinearLayout

    //默认组件
    private var backIcon: ImageView? = null
    private var menuIcon: ImageView? = null
    private var titleText: TextView? = null

    init {
        inflate(context, R.layout.layout_head_layout, this)

        leftContainer = findViewById(R.id.left_container)
        centerContainer = findViewById(R.id.center_container)
        rightContainer = findViewById(R.id.right_container)
        headLinear =  findViewById(R.id.head_linear)
    }

    fun setHeadLayoutColor(){
        headLinear.setBackgroundResource(R.color.color_FFFFFF)
    }

    fun setTitle(resId: Int) {
        setTitle(resources.getString(resId))
    }

    fun setTitle(title: String) {
        if (titleText == null) {
            bindView(centerContainer, R.layout.layout_head_title)
            titleText = centerContainer.findViewById(R.id.title_text)
        }
        titleText?.text = title
    }

    fun setBackListener(listener: OnClickListener) {
        if (backIcon == null) {
            bindView(leftContainer, R.layout.layout_head_back)
            backIcon = leftContainer.findViewById(R.id.back_image)
        }
        backIcon?.setOnClickListener(listener)
    }

    fun setMenuListener(listener: OnClickListener){
        if (menuIcon == null) {
            bindView(rightContainer, R.layout.layout_head_menu)
            menuIcon = rightContainer.findViewById(R.id.menu_image)
        }
        menuIcon?.setOnClickListener(listener)
    }

    fun bindLeftView(childView: View) {
        bindView(leftContainer, childView)
    }

    fun bindCenterView(childView: View) {
        bindView(centerContainer, childView)
    }

    fun bindRightView(childView: View) {
        bindView(rightContainer, childView)
    }

    fun bindView(parent: ViewGroup, childLayoutId: Int) {
        bindView(parent, LayoutInflater.from(context).inflate(childLayoutId, parent, false))
    }

    fun bindView(parent: ViewGroup, childView: View) {
        parent.removeAllViews()
        parent.addView(childView)
    }


}