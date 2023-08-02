package com.example.myapplication.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.database.entity.NewsInfo
import com.example.myapplication.databinding.LayoutNewsListItemBinding
import com.example.myapplication.util.GlideImageLoader
import com.example.myapplication.util.TimeUtil
import com.example.myapplication.util.layoutInflater

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.ui.adapter
 * @ClassName : NewsListAdapter
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/7/17 9:55
 * @UpdateUser : yulu
 * @UpdateDate : 2023/7/17 9:55
 * @UpdateRemark : 更新说明
 */
class NewsListAdapter : RecyclerView.Adapter<NewsListAdapter.NewsListViewHolder>() {

    var list: List<NewsInfo> = listOf()
    var goNewsDetailListener: (NewsInfo) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsListViewHolder {
        return NewsListViewHolder(parent)
    }

    override fun onBindViewHolder(holder: NewsListViewHolder, position: Int) {
        holder.setData(position, list[position], goNewsDetailListener)
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

        fun setData(position: Int, news: NewsInfo, goNewsDetailListener: (NewsInfo) -> Unit) {
            binding.newsAuthor.text = news.number.toString()
            binding.newsTag.text = news.tag
            binding.newsContent.text = news.content
            binding.newsDate.text = TimeUtil.millis2String(news.time, TimeUtil.dateFormatYMD_CN)
            binding.newsTitle.text = news.title
            GlideImageLoader().displayLocalFile(news.image, binding.newsImage)
            binding.newsItem.setOnClickListener {
                goNewsDetailListener.invoke(news)
            }
        }
    }
}