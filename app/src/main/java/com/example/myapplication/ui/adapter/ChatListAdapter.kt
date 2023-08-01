package com.example.myapplication.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import coil.transform.RoundedCornersTransformation
import coil.util.CoilUtils
import com.example.myapplication.R
import com.example.myapplication.config.AppConfig
import com.example.myapplication.database.entity.ChatInfo
import com.example.myapplication.databinding.LayoutChatListItemBinding
import com.example.myapplication.databinding.LayoutImageItemBinding
import com.example.myapplication.util.ImageUtil
import com.example.myapplication.util.TimeUtil

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.ui.adapter
 * @ClassName : ChatListAdapter
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/7/26 17:26
 * @UpdateUser : yulu
 * @UpdateDate : 2023/7/26 17:26
 * @UpdateRemark : 更新说明
 */
class ChatListAdapter : RecyclerView.Adapter<ChatListAdapter.ChatListViewHolder>() {

    var list: List<ChatInfo> = listOf()
    var goUserDetailListener: (Long) -> Unit = { }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatListViewHolder {
        return ChatListViewHolder(parent)
    }

    override fun onBindViewHolder(holder: ChatListViewHolder, position: Int) {
        holder.setData(
            position,
            list[position],
            goUserDetailListener
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ChatListViewHolder(
        parent: ViewGroup,
        val binding: LayoutChatListItemBinding = LayoutChatListItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    ) : RecyclerView.ViewHolder(binding.root) {
        fun setData(
            position: Int,
            chat: ChatInfo,
            goUserDetailListener: (Long) -> Unit
        ) {
            if (chat.sendTag == AppConfig.phoneNumber) {
                binding.leftChat.visibility = View.GONE
                binding.rightChat.visibility = View.VISIBLE
                binding.rightContent.text = chat.content
                binding.rightTime.text = TimeUtil.getFriendlyTimeSpanByNow(chat.time)
                binding.rightIcon.setOnClickListener {
                    goUserDetailListener.invoke(chat.sendTag)
                }
            } else {
                binding.rightChat.visibility = View.GONE
                binding.leftChat.visibility = View.VISIBLE
                binding.leftContent.text = chat.content
                binding.leftTime.text = TimeUtil.getFriendlyTimeSpanByNow(chat.time)
                binding.leftIcon.setOnClickListener{
                    goUserDetailListener.invoke(chat.sendTag)
                }
            }
        }
    }
}