package com.example.myapplication.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.database.entity.CommentInfo
import com.example.myapplication.database.entity.User
import com.example.myapplication.databinding.LayoutReplyListItemBinding
import com.example.myapplication.util.layoutInflater

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.ui.adapter
 * @ClassName : ReplyListAdapter
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/7/28 15:20
 * @UpdateUser : yulu
 * @UpdateDate : 2023/7/28 15:20
 * @UpdateRemark : 更新说明
 */
class ReplyListAdapter : RecyclerView.Adapter<ReplyListAdapter.ReplyListViewHolder>() {

    var list: Map<CommentInfo, User> = mapOf()
    var goUserDetail: (Long) -> Unit = {}
    var deleteCommentListener: (CommentInfo) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReplyListViewHolder {
        return ReplyListViewHolder(parent)
    }

    override fun onBindViewHolder(holder: ReplyListViewHolder, position: Int) {
        holder.setData(
            list.keys.toList()[position],
            list.values.toList()[position],
            goUserDetail,
            deleteCommentListener
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ReplyListViewHolder(
        var parent: ViewGroup,
        val binding: LayoutReplyListItemBinding = LayoutReplyListItemBinding.inflate(
            parent.context.layoutInflater(),
            parent,
            false
        )
    ) : RecyclerView.ViewHolder(binding.root) {
        fun setData(
            comment: CommentInfo,
            user: User,
            goUserDetail: (Long) -> Unit,
            deleteCommentListener: (CommentInfo) -> Unit
        ) {
            binding.replyContent.text = comment.content
            binding.replyNeck.text = user.neck
            // 一般来说 回调时去上一层实现 上一层实现不了 就去上上层实现 以此类推 总能实现
            // 原因：UI需要在Activity/Fragment中去实现
            // 由于ReplyListAdapter上一层还是个适配器 所以需要去上上层 也就是NewsDetailFragment中实现
            binding.replyItem.setOnClickListener {
                goUserDetail.invoke(comment.number)
            }
            binding.replyItem.setOnLongClickListener {
                deleteCommentListener.invoke(comment)
                true
            }
        }
    }

}