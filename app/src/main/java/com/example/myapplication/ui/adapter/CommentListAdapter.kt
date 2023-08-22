package com.example.myapplication.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.DataManager
import com.example.myapplication.database.entity.CommentInfo
import com.example.myapplication.databinding.LayoutCommentListItemBinding
import com.example.myapplication.util.layoutInflater

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.ui.adapter
 * @ClassName : CommentListAdapter
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/7/28 9:45
 * @UpdateUser : yulu
 * @UpdateDate : 2023/7/28 9:45
 * @UpdateRemark : 更新说明
 */
class CommentListAdapter : RecyclerView.Adapter<CommentListAdapter.CommentListViewHolder>() {

    var list = listOf<CommentInfo>()

    var addFriendListener: (Int) -> Unit = {}
    var goCommentListener: (Long) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentListViewHolder {
        return CommentListViewHolder(parent)
    }

    override fun onBindViewHolder(holder: CommentListViewHolder, position: Int) {
        holder.setData(position, list[position], goCommentListener)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class CommentListViewHolder(
        var parent: ViewGroup,
        val binding: LayoutCommentListItemBinding = LayoutCommentListItemBinding.inflate(
            parent.context.layoutInflater(),
            parent,
            false
        )
    ) : RecyclerView.ViewHolder(binding.root) {
        var replyListAdapter = ReplyListAdapter()
        fun setData(position: Int, commentInfo: CommentInfo, goCommentListener: (Long) -> Unit) {

            binding.goComment.setOnClickListener {
                goCommentListener.invoke(commentInfo.id)
            }

            if (position % 2 == 0) {
                binding.replyList.apply {
                    layoutManager = DataManager.layoutManagerNotScroll()
                    adapter = replyListAdapter
                }
                replyListAdapter.list = listOf("你是谁？")
                replyListAdapter.notifyDataSetChanged()
                listRefresh()
            } else {
                binding.replyList.apply {
                    layoutManager = DataManager.layoutManagerNotScroll()
                    adapter = replyListAdapter
                }
                replyListAdapter.list = listOf("哈哈哈", "阿里嘎多")
                replyListAdapter.notifyDataSetChanged()
                listRefresh()
            }


        }

        private fun listRefresh() {
            replyListAdapter.notifyDataSetChanged()
        }
    }

}