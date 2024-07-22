package com.example.myapplication.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.database.entity.ChatInfo
import com.example.myapplication.database.entity.User
import com.example.myapplication.databinding.LayoutSearchChatsItemBinding
import com.example.myapplication.util.GlideImageLoader
import com.example.myapplication.util.TimeUtil

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.ui.adapter
 * @ClassName : ChatListAdapter
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/12/14 17:22
 * @UpdateUser : yulu
 * @UpdateDate : 2023/12/14 17:22
 * @UpdateRemark : 更新说明
 */
class SearchChatsAdapter : RecyclerView.Adapter<SearchChatsAdapter.ChatListViewHolder>() {

    var list: Map<ChatInfo, User> = mapOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatListViewHolder {
        return ChatListViewHolder(parent)
    }

    override fun onBindViewHolder(holder: ChatListViewHolder, position: Int) {
        holder.setData(
            list.keys.toList()[position],
            list.values.toList()[position]
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ChatListViewHolder(
        parent: ViewGroup,
        val binding: LayoutSearchChatsItemBinding = LayoutSearchChatsItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    ) : RecyclerView.ViewHolder(binding.root) {
        fun setData(
            chat: ChatInfo,
            user: User,
        ) {
            GlideImageLoader().displayLocalFile(user.image, binding.searchChatIcon)
            binding.searchChatContent.text = chat.content
            binding.searchChatTime.text = TimeUtil.getFriendlyTimeSpanByNow(chat.time)
            binding.searchChatNeck.text = user.neck
        }
    }
}