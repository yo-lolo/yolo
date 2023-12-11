package com.example.myapplication.admin.adpter

import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.database.entity.NewsInfo
import com.example.myapplication.databinding.AdiminLayoutNewsListItemBinding
import com.example.myapplication.util.GlideImageLoader
import com.example.myapplication.util.TimeUtil
import com.example.myapplication.util.layoutInflater
import com.example.myapplication.util.unClick
import com.example.myapplication.util.visibleOrGone

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.ui.adapter
 * @ClassName : NewsListAdapter
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/8/22 15:14
 * @UpdateUser : yulu
 * @UpdateDate : 2023/8/22 15:14
 * @UpdateRemark : 更新说明
 */
class AdminNewsListAdapter : RecyclerView.Adapter<AdminNewsListAdapter.NewsListViewHolder>() {

    var list: List<NewsInfo> = listOf()
    var showContentListener: (NewsInfo) -> Unit = {}
    var auditPassListener: (NewsInfo) -> Unit = {}
    var auditFailListener: (NewsInfo) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsListViewHolder {
        return NewsListViewHolder(parent)
    }

    override fun onBindViewHolder(holder: NewsListViewHolder, position: Int) {
        holder.setData(
            list[position],
            showContentListener,
            auditPassListener,
            auditFailListener
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class NewsListViewHolder(
        parent: ViewGroup,
        private val binding: AdiminLayoutNewsListItemBinding = AdiminLayoutNewsListItemBinding.inflate(
            parent.context.layoutInflater(),
            parent,
            false
        )
    ) : RecyclerView.ViewHolder(binding.root) {

        fun setData(
            news: NewsInfo,
            showContentListener: (NewsInfo) -> Unit,
            auditPassListener: (NewsInfo) -> Unit,
            auditFailListener: (NewsInfo) -> Unit
        ) {
            binding.auditPass.unClick(news.type != 0)
            binding.auditFail.unClick(news.type != 0)
            binding.newsAuthor.text = "账户：${news.number.toString()}"
            binding.newsTag.text = "标签：${news.tag}"
            binding.newsContent.text = "内容：${news.content}"
            binding.newsDate.text =
                "发布时间：${TimeUtil.millis2String(news.time, TimeUtil.dateFormatYMD_CN)}"
            binding.newsTitle.text = "标题：${news.title}"
            GlideImageLoader().displayLocalFile(news.image, binding.newsImage)
            binding.showDetail.setOnClickListener {
                showContentListener.invoke(news)
            }
            binding.newsItem.setOnClickListener {
                binding.auditLayout.visibleOrGone(!binding.auditLayout.isVisible)
            }
            binding.auditPass.setOnClickListener {
                auditPassListener.invoke(news)
            }
            binding.auditFail.setOnClickListener {
                auditFailListener.invoke(news)
            }
        }
    }
}