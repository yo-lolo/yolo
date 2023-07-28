package com.example.myapplication.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.LayoutCommentListItemBinding
import com.example.myapplication.databinding.LayoutReplyListItemBinding
import com.example.myapplication.util.layoutInflater

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.ui.adapter
 * @ClassName : CommentListAdapter
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/7/28 15:20
 * @UpdateUser : yulu
 * @UpdateDate : 2023/7/28 15:20
 * @UpdateRemark : 更新说明
 */
class ReplyListAdapter : RecyclerView.Adapter<ReplyListAdapter.ReplyListViewHolder>() {

    var list = listOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReplyListViewHolder {
        return ReplyListViewHolder(parent)
    }

    override fun onBindViewHolder(holder: ReplyListViewHolder, position: Int) {
        holder.setData(position, list[position])
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
        fun setData(position: Int, data: String) {
            binding.replyContent.text = data
        }
    }

}