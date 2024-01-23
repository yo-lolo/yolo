package com.example.myapplication.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.database.entity.CommentInfo
import com.example.myapplication.database.entity.User
import com.example.myapplication.databinding.LayoutTestItemBinding
import com.example.myapplication.databinding.LayoutTestTwoItemBinding
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
 * @CreateDate : 2023/7/28 9:45
 * @UpdateUser : yulu
 * @UpdateDate : 2023/7/28 9:45
 * @UpdateRemark : 更新说明
 */
class CommentsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var list: Map<CommentInfo, User> = mapOf()
    var goCommentListener: (Long) -> Unit = {}
    var goUserDetail: (Long) -> Unit = {}
    var deleteCommentListener: (CommentInfo) -> Unit = {}


    private val LEVEL_ONE_VIEW = 0 // 一级布局的的ViewType
    private val LEVEL_TWO_VIEW = 1 // 二级布局的的ViewType

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == LEVEL_ONE_VIEW) {
            FirstViewHolder(parent)
        } else {
            SecondViewHolder(parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == LEVEL_ONE_VIEW) {
            holder as FirstViewHolder
            holder.setData(
                list.toList()[position],
                goCommentListener,
                goUserDetail,
                deleteCommentListener
            )
        } else {
            holder as SecondViewHolder
            holder.setData(
                list.toList()[position],
                goUserDetail,
                deleteCommentListener
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        val commentInfo = list.toList()[position].first
        return if (commentInfo.level == 1) {
            LEVEL_ONE_VIEW
        } else {
            LEVEL_TWO_VIEW
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class FirstViewHolder(
        var parent: ViewGroup,
        val binding: LayoutTestItemBinding = LayoutTestItemBinding.inflate(
            parent.context.layoutInflater(), parent, false
        )
    ) : RecyclerView.ViewHolder(binding.root) {

        fun setData(
            pair: Pair<CommentInfo, User>,
            goCommentListener: (Long) -> Unit,
            goUserDetail: (Long) -> Unit,
            deleteCommentListener: (CommentInfo) -> Unit
        ) {
            val commentInfo = pair.first
            val user = pair.second
            binding.commentTime.text = TimeUtil.millis2String(commentInfo.time)
            binding.commentNeck.text = user.neck
            binding.commentContent.text = commentInfo.content
            GlideImageLoader().displayLocalFile(user.image, binding.commentIcon)
            // 一些回调函数 具体实现在NewsDetailFragment中 invoke中的值是传入的参数
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
        }
    }

    class SecondViewHolder(
        var parent: ViewGroup,
        val binding: LayoutTestTwoItemBinding = LayoutTestTwoItemBinding.inflate(
            parent.context.layoutInflater(), parent, false
        )
    ) : RecyclerView.ViewHolder(binding.root) {

        fun setData(
            pair: Pair<CommentInfo, User>,
            goUserDetail: (Long) -> Unit,
            deleteCommentListener: (CommentInfo) -> Unit
        ) {
            val commentInfo = pair.first
            val user = pair.second
            binding.replyContent.text = commentInfo.content
            binding.replyNeck.text = user.neck
            itemView.setOnLongClickListener {
                deleteCommentListener.invoke(commentInfo)
                true
            }
            itemView.setOnClickListener {
                goUserDetail.invoke(user.number)
            }
        }
    }


}