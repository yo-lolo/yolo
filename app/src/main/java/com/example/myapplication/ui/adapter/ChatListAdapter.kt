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
import com.example.myapplication.databinding.LayoutChatListItemBinding
import com.example.myapplication.databinding.LayoutImageItemBinding
import com.example.myapplication.util.ImageUtil

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.ui.adapter
 * @ClassName : ImageDisplayAdapter
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/7/26 17:26
 * @UpdateUser : yulu
 * @UpdateDate : 2023/7/26 17:26
 * @UpdateRemark : 更新说明
 */
class ChatListAdapter : RecyclerView.Adapter<ChatListAdapter.ChatListViewHolder>() {

    var list = mutableListOf<String>("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "")


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatListViewHolder {
        return ChatListViewHolder(parent)
    }

    override fun onBindViewHolder(holder: ChatListViewHolder, position: Int) {
        holder.setData(
            position,
            list[position]
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
            data: String
        ) {
            if (position % 2 != 0){
                binding.leftChat.visibility = View.VISIBLE
                binding.rightChat.visibility = View.GONE
            }else{
                binding.leftChat.visibility = View.GONE
                binding.rightChat.visibility = View.VISIBLE
            }
        }
    }
}