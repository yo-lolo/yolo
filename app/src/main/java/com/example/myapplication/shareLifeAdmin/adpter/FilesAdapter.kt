package com.example.myapplication.shareLifeAdmin.adpter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.LayoutAdminFileViewBinding
import com.example.myapplication.util.layoutInflater

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.admin.adpter
 * @ClassName : FilesAdapter
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/8/22 16:39
 * @UpdateUser : yulu
 * @UpdateDate : 2023/8/22 16:39
 * @UpdateRemark : 更新说明
 */
class FilesAdapter : RecyclerView.Adapter<FilesAdapter.FilesViewHolder>() {
    private val titles = listOf("用户", "反馈", "文章", "评论")
    private val navIds = listOf(
        R.id.goAdminUserFragment,
        R.id.goAdminFeedbackFragment,
        R.id.goAdminNewsFragment,
        R.id.goAdminCommentFragment
    )
    private val iconRes = listOf(
        R.mipmap.admin_user_list_icon,
        R.mipmap.admin_feed_list_icon,
        R.mipmap.admin_news_list_icon,
        R.mipmap.admin_comment_list_icon,
    )
    var clickListener: (Int) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilesViewHolder {
        return FilesViewHolder(parent)
    }

    override fun onBindViewHolder(holder: FilesViewHolder, position: Int) {
        holder.setData(titles[position], navIds[position], iconRes[position], clickListener)
    }

    override fun getItemCount(): Int {
        return navIds.size
    }

    class FilesViewHolder(
        parent: ViewGroup,
        val binding: LayoutAdminFileViewBinding = LayoutAdminFileViewBinding.inflate(
            parent.context.layoutInflater(),
            parent,
            false
        )
    ) : RecyclerView.ViewHolder(binding.root) {
        fun setData(title: String, navId: Int, iconRes: Int, clickListener: (Int) -> Unit) {
            binding.type.text = title
            binding.icon.setImageResource(iconRes)
            itemView.setOnClickListener { clickListener.invoke(navId) }
        }
    }


}