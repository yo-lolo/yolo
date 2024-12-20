package com.example.myapplication.shareLifeAdmin.adpter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.LayoutManageImagesItemBinding
import com.example.myapplication.util.GlideImageLoader
import com.example.myapplication.util.layoutInflater

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.admin.adpter
 * @ClassName : ImagesAdapter
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/8/22 15:12
 * @UpdateUser : yulu
 * @UpdateDate : 2023/8/22 15:12
 * @UpdateRemark : 更新说明
 */
class ImagesAdapter : RecyclerView.Adapter<ImagesAdapter.ImagesViewHolder>() {

    var list: List<String> = listOf()
    var showBigImageListener: (String) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagesViewHolder {
        return ImagesViewHolder(parent)
    }

    override fun onBindViewHolder(holder: ImagesViewHolder, position: Int) {
        holder.setData(list[position], showBigImageListener)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ImagesViewHolder(
        parent: ViewGroup,
        val binding: LayoutManageImagesItemBinding = LayoutManageImagesItemBinding.inflate(
            parent.context.layoutInflater(),
            parent,
            false
        )
    ) : RecyclerView.ViewHolder(binding.root) {
        fun setData(data: String, showBigImageListener: (String) -> Unit) {
            GlideImageLoader().displayLocalFile(data, binding.imageItem)
            binding.imageItem.setOnClickListener {
                showBigImageListener.invoke(data)
            }
        }
    }
}