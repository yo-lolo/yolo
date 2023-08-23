package com.example.myapplication.ui.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.DataManager
import com.example.myapplication.database.entity.CommentInfo
import com.example.myapplication.databinding.LayoutCommentListItemBinding
import com.example.myapplication.util.TimeUtil
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

    var allList = listOf<CommentInfo>()
    var goCommentListener: (Long) -> Unit = {}
    var goUserDetail: (Long) -> Unit = {}
    var deleteCommentListener: (CommentInfo) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentListViewHolder {
        return CommentListViewHolder(parent)
    }

    override fun onBindViewHolder(holder: CommentListViewHolder, position: Int) {
        var list = allList.filter { it.level == 1 }
        var replyList = allList.filter { it.level == 2 }
        holder.setData(
            list[position],
            goCommentListener,
            goUserDetail,
            replyList,
            deleteCommentListener
        )
    }

    override fun getItemCount(): Int {
        return allList.filter { it.level == 1 }.size
    }

    class CommentListViewHolder(
        var parent: ViewGroup,
        val binding: LayoutCommentListItemBinding = LayoutCommentListItemBinding.inflate(
            parent.context.layoutInflater(), parent, false
        )
    ) : RecyclerView.ViewHolder(binding.root) {
        var replyListAdapter = ReplyListAdapter()

        fun setData(
            commentInfo: CommentInfo,
            goCommentListener: (Long) -> Unit,
            goUserDetail: (Long) -> Unit,
            replyList: List<CommentInfo>,
            deleteCommentListener: (CommentInfo) -> Unit
        ) {
            binding.commentTime.text = TimeUtil.millis2String(commentInfo.time)
            binding.commentNeck.text = commentInfo.number.toString()
            binding.commentContent.text = commentInfo.content

            // 一些回调函数 具体实现在NewsDetailFragment中 invoke中的值时传入的参数
            binding.goComment.setOnClickListener {
                goCommentListener.invoke(commentInfo.id)
            }
            binding.mainComment.setOnClickListener {
                goUserDetail.invoke(commentInfo.number)
            }
            binding.mainComment.setOnLongClickListener {
                deleteCommentListener.invoke(commentInfo)
                true
            }

            // 过滤出该条评论下的评论 即replyId == commentInfo.id
            val currentReplyList =
                replyList.filter { it.replyId == commentInfo.id }
            binding.replyList.visibility =
                if (currentReplyList.isNotEmpty()) View.VISIBLE else View.GONE
            binding.replyList.apply {
                layoutManager = DataManager.layoutManagerNotScroll()
                adapter = replyListAdapter
            }
            replyListAdapter.apply {
                list = currentReplyList
                notifyDataSetChanged()
            }
            // 回调 这里因为RecyclerView中嵌套了一个RecyclerView 所以replyListAdapter中的回调要传两层
            replyListAdapter.goUserDetail = {
                goUserDetail.invoke(it)
            }
            replyListAdapter.deleteCommentListener = {
                deleteCommentListener.invoke(it)
            }
        }
    }

}