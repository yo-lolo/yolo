package com.example.myapplication.admin.adpter

import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.DataManager
import com.example.myapplication.database.entity.CommentInfo
import com.example.myapplication.database.entity.FeedbackInfo
import com.example.myapplication.databinding.AdiminLayoutCommentsItemBinding
import com.example.myapplication.databinding.AdiminLayoutNewsListItemBinding
import com.example.myapplication.databinding.LayoutCommentListItemBinding
import com.example.myapplication.databinding.LayoutManageFeedbacksItemBinding
import com.example.myapplication.util.JsonUtil
import com.example.myapplication.util.TimeUtil
import com.example.myapplication.util.layoutInflater
import com.google.gson.reflect.TypeToken
import java.util.ArrayList

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.admin.adpter
 * @ClassName : FeedbacksAdapter
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/12/12 10:40
 * @UpdateUser : yulu
 * @UpdateDate : 2023/12/12 10:40
 * @UpdateRemark : 更新说明
 */
class AdminCommentsAdapter : RecyclerView.Adapter<AdminCommentsAdapter.CommentsViewHolder>() {

    var list: List<CommentInfo> = listOf()
    var checkedChangeListener: () -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentsViewHolder {
        return CommentsViewHolder(parent)
    }

    override fun onBindViewHolder(holder: CommentsViewHolder, position: Int) {
        holder.setData(list[position], checkedChangeListener)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class CommentsViewHolder(
        parent: ViewGroup,
        val binding: AdiminLayoutCommentsItemBinding = AdiminLayoutCommentsItemBinding.inflate(
            parent.context.layoutInflater(),
            parent,
            false
        )
    ) : RecyclerView.ViewHolder(binding.root) {

        fun setData(
            commentInfo: CommentInfo,
            checkedChangeListener: () -> Unit
        ) {
            binding.commentCheckBox.setOnCheckedChangeListener(null)
            binding.commentCheckBox.isChecked = commentInfo.checked
            binding.commentCheckBox.setOnCheckedChangeListener { _, isChecked ->
                commentInfo.checked = isChecked
                checkedChangeListener.invoke()
            }
            binding.commentsUser.text = "用户名："+commentInfo.number.toString()
            binding.commentsContent.text = "评论内容："+commentInfo.content

        }
    }
}

