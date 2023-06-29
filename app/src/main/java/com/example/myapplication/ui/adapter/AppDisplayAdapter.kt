package com.example.myapplication.ui.adapter

import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.myapplication.database.entity.ApkInfo
import com.example.myapplication.databinding.LayoutAppDisplayItemBinding
import com.example.myapplication.util.ImageUtil
import com.example.myapplication.util.layoutInflater

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : safe-phone
 * @Package : com.ctq.sphone.market.ui.adapter
 * @ClassName : 类名
 * @Description : 文件描述
 * @Author : hulifei
 * @CreateDate : 2023/2/2 19:05
 * @UpdateUser : hulifei
 * @UpdateDate : 2023/2/2 19:05
 * @UpdateRemark : 更新说明
 */
class AppDisplayAdapter : RecyclerView.Adapter<AppDisplayAdapter.AppDisplayViewHodler>() {
    var list = listOf<ApkInfo>()

    var fragmentLifecycle: Lifecycle? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppDisplayViewHodler {
        return AppDisplayViewHodler(parent)
    }

    override fun onBindViewHolder(holder: AppDisplayViewHodler, position: Int) {
        holder.setData(list[position], fragmentLifecycle!!)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class AppDisplayViewHodler(
        parent: ViewGroup,
        private val viewBinding: LayoutAppDisplayItemBinding = LayoutAppDisplayItemBinding.inflate(
            parent.context.layoutInflater(), parent, false
        )
    ) : ViewHolder(viewBinding.root) {

        fun setData(
            apkInfo: ApkInfo,
            fragmentLifecycle: Lifecycle,
        ) {
            ImageUtil.displayAppIcon(viewBinding.iconImage, apkInfo.action)

            itemView.setOnClickListener {
                // TODO
            }
        }

    }

}