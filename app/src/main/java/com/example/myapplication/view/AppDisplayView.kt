package com.example.myapplication.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.database.entity.ApkInfo
import com.example.myapplication.databinding.LayoutAppDisplayViewBinding
import com.example.myapplication.ui.adapter.AppDisplayAdapter

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.view
 * @ClassName : AppDisplayView
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/6/29 16:13
 * @UpdateUser : yulu
 * @UpdateDate : 2023/6/29 16:13
 * @UpdateRemark : 更新说明
 */
class AppDisplayView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    private val viewBinding: LayoutAppDisplayViewBinding

    private val appsAdapter = AppDisplayAdapter()

    init {
        viewBinding = LayoutAppDisplayViewBinding.inflate(LayoutInflater.from(context), this, true)
        viewBinding.displayAppsRecyclew.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            adapter = appsAdapter
        }
    }

    fun setTitle(titleId: Int) {
        setTitle(resources.getString(titleId))
    }

    fun setTitle(titleStr: String) {
        viewBinding.textTitle.text = titleStr
    }

    fun setSubTitle(subTitleId: Int, listener: OnClickListener) {
        setSubTitle(subTitleId, listener)
    }

    fun setSubTitle(subTitleStr: String, listener: OnClickListener) {
        viewBinding.textSubTitle.visibility = VISIBLE
        viewBinding.textSubTitle.text = subTitleStr
        viewBinding.textSubTitle.setOnClickListener(listener)
    }

    fun setDatas(fragmentLifecycle: Lifecycle, data: List<ApkInfo>) {
        appsAdapter.fragmentLifecycle=fragmentLifecycle
        appsAdapter.list = data
        appsAdapter.notifyDataSetChanged()
    }

}