package com.example.myapplication.ui.adapter

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.util.CoilUtils
import com.example.myapplication.R
import com.example.myapplication.data.MineComments
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

    var list: Map<CommentInfo, MineComments> = mapOf()
    var goCommentListener: (Long, Long) -> Unit = { newsId, commentId -> }
    var goUserDetailListener: (Long) -> Unit = {}
    var goNewsDetailListener: (Long) -> Unit = {}

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

        @SuppressLint("SetTextI18n", "ResourceAsColor")
        fun setData(
            comment: CommentInfo,
            aLot: MineComments,
            goCommentListener: (Long, Long) -> Unit,
            goNewsDetailListener: (Long) -> Unit,
            goUserDetailListener: (Long) -> Unit,
        ) {
            val reply = aLot.replyComment
            val user2 = aLot.user2
            val news = aLot.newsInfo
            val user = aLot.user
            val newsUser = aLot.newsUser
            if (comment.level == 2) {
                binding.visibleReply1.visibility = View.VISIBLE
                binding.replyItem.visibility = View.VISIBLE
                binding.replyNeck.text = "@" + user2?.neck + ":"
                binding.replyNeck2.text = "@" + user2?.neck + ":"
                binding.replyContent.text = reply?.content ?: "该条评论已删除"
            } else {
                binding.visibleReply1.visibility = View.GONE
                binding.replyItem.visibility = View.GONE
            }
            if (news != null && user != null) {
                binding.commentTime.text =
                    TimeUtil.millis2String(comment.time, TimeUtil.dateFormatYMDHM)
                binding.commentNeck.text = user.neck
                GlideImageLoader().displayLocalFile(user.image, binding.commentIcon)
                binding.commentContent.text = comment.content

                // 一些回调函数 具体实现在NewsDetailFragment中 invoke中的值时传入的参数
                binding.goComment.setOnClickListener {
                    goCommentListener.invoke(news.id, comment.id)
                }
                binding.newsTitle.text = news.title
                binding.newsAuthor.text = "@" + newsUser?.neck
                GlideImageLoader().displayLocalFile(news.image, binding.newsImage)
                binding.newsItem.setOnClickListener {
                    goNewsDetailListener.invoke(news.id)
                }
                binding.goUserDetail.setOnClickListener {
                    goUserDetailListener.invoke(comment.number)
                }
            } else {
                GlideImageLoader().displayImageError("", binding.newsImage)
                CoilUtils.dispose(binding.newsImage)
                binding.newsMess.visibility = View.GONE
                binding.newsNullMess.visibility = View.VISIBLE
            }
        }
    }

}