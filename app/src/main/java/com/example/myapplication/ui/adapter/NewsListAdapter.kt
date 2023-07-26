package com.example.myapplication.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewParent
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.ToastUtils
import com.example.myapplication.databinding.LayoutNewsListItemBinding
import com.example.myapplication.databinding.LayoutTwiceListItemBinding
import com.example.myapplication.util.layoutInflater

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.ui.adapter
 * @ClassName : TwiceListAdapter
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/7/17 9:55
 * @UpdateUser : yulu
 * @UpdateDate : 2023/7/17 9:55
 * @UpdateRemark : 更新说明
 */
class NewsListAdapter : RecyclerView.Adapter<NewsListAdapter.NewsListViewHolder>() {

    var list = listOf("", "", "", "", "", "", "", "", "")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsListViewHolder {
        return NewsListViewHolder(parent)
    }

    override fun onBindViewHolder(holder: NewsListViewHolder, position: Int) {
        holder.setData(position, list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class NewsListViewHolder(
        parent: ViewGroup,
        private val binding: LayoutNewsListItemBinding = LayoutNewsListItemBinding.inflate(
            parent.context.layoutInflater(),
            parent,
            false
        )
    ) : RecyclerView.ViewHolder(binding.root) {

        fun setData(position: Int, data: String) {

        }

    }
}