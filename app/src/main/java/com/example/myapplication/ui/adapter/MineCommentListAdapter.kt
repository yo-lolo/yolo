package com.example.myapplication.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.database.entity.CommentInfo
import com.example.myapplication.database.entity.NewsInfo
import com.example.myapplication.databinding.LayoutMineCommentListItemBinding
import com.example.myapplication.util.GlideImageLoader
import com.example.myapplication.util.TimeUtil
import com.example.myapplication.util.layoutInflater

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.ui.adapter
 * @ClassName : CommentListAdapter
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/8/24 11:27
 * @UpdateUser : yulu
 * @UpdateDate : 2023/8/24 11:27
 * @UpdateRemark : 更新说明
 */
class MineCommentListAdapter :
    RecyclerView.Adapter<MineCommentListAdapter.CommentListViewHolder>() {

    var list: Map<CommentInfo, NewsInfo> = mapOf()
    var goCommentListener: (Long, Long) -> Unit = { newsId, commentId -> }
    var goUserDetailListener: (Long) -> Unit = {}
    var goNewsDetailListener: (NewsInfo) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentListViewHolder {
        return CommentListViewHolder(parent)
    }

    override fun onBindViewHolder(holder: CommentListViewHolder, position: Int) {
        holder.setData(
            list.keys.toList()[position],
            list.values.toList()[position],
            goCommentListener,
            goNewsDetailListener,
            goUserDetailListener
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class CommentListViewHolder(
        var parent: ViewGroup,
        val binding: LayoutMineCommentListItemBinding = LayoutMineCommentListItemBinding.inflate(
            parent.context.layoutInflater(), parent, false
        )
    ) : RecyclerView.ViewHolder(binding.root) {

        fun setData(
            comment: CommentInfo,
            news: NewsInfo,
            goCommentListener: (Long, Long) -> Unit,
            goNewsDetailListener: (NewsInfo) -> Unit,
            goUserDetailListener: (Long) -> Unit,
        ) {
            binding.commentTime.text =
                TimeUtil.millis2String(comment.time, TimeUtil.dateFormatYMDHM)
            binding.commentNeck.text = comment.number.toString()
            binding.commentContent.text = comment.content

            // 一些回调函数 具体实现在NewsDetailFragment中 invoke中的值时传入的参数
            binding.goComment.setOnClickListener {
                goCommentListener.invoke(news.id, comment.id)
            }
            binding.newsTitle.text = news.title
            binding.newsAuthor.text = news.number.toString()
            GlideImageLoader().displayLocalFile(news.image, binding.newsImage)
            binding.newsItem.setOnClickListener {
                goNewsDetailListener.invoke(news)
            }
            binding.goUserDetail.setOnClickListener {
                goUserDetailListener.invoke(comment.number)
            }
        }
    }

}