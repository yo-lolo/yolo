package com.example.myapplication.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.ResolveInfo
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewbinding.ViewBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.myapplication.R
import com.example.myapplication.databinding.PopSelectBrowserBinding
import com.lxj.xpopup.core.BottomPopupView


/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName :
 * @Package : SslVpn
 * @ClassName : com.ctq.sslvpn.pops
 * @Description : 文件描述
 * @Author : Yulu
 * @CreateDate : 2023/11/8 11:37
 * @UpdateUser : Yulu
 * @UpdateDate : 2023/11/8 11:37
 * @UpdateRemark : 更新说明
 */
@SuppressLint("ViewConstructor")
class SelectBrowserPop(context: Context, private val browserList: List<ResolveInfo>) :
    BottomPopupView(context) {


    private lateinit var viewBinding: PopSelectBrowserBinding

    private lateinit var browserAdapter: BaseQuickAdapter<ResolveInfo, BaseViewHolder>

    var onSelectBrowser: (ResolveInfo) -> Unit = {}

    private val gridSpanCount = 4

    override fun addInnerContent() {
        viewBinding = bottomPopupContainer.binding()
    }

    override fun onCreate() {
        super.onCreate()
        initSelectBrowserRecyclerView()
    }

    /**
     * 从 ViewGroup 中获取并绑定 ViewBinding， 此时 ViewBinding 对应的布局会被添加到 ViewGroup 上
     * 通过 ViewGroup 获取 VB
     */
    inline fun <reified VB : ViewBinding> ViewGroup.binding() =
        VB::class.java.getMethod(
            "inflate",
            LayoutInflater::class.java,
            ViewGroup::class.java,
            Boolean::class.java
        ).invoke(null, LayoutInflater.from(this.context), this, true) as VB


    private fun initSelectBrowserRecyclerView() {
        browserAdapter = object : BaseQuickAdapter<ResolveInfo, BaseViewHolder>(
            R.layout.item_select_browser,
            browserList.toMutableList()
        ) {

            override fun convert(holder: BaseViewHolder, item: ResolveInfo) {
                holder.getView<ImageView>(R.id.item_browser_image)
                    .setImageDrawable(item.getAppIcon(context))
                holder.getView<TextView>(R.id.item_browser_name).text = item.getAppName(context)
            }
        }

        browserAdapter.setOnItemClickListener { _, _, pos ->
            onSelectBrowser.invoke(browserList[pos])
            dismiss()
        }

        viewBinding.browserRecyclerview.run {
            this.layoutManager = GridLayoutManager(context, gridSpanCount)
            this.adapter = browserAdapter
        }
    }


}