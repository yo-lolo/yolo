package com.example.myapplication.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.database.entity.FriendInfo
import com.example.myapplication.database.entity.User
import com.example.myapplication.databinding.LayoutFriendListItemBinding
import com.example.myapplication.util.GlideImageLoader
import com.example.myapplication.util.layoutInflater

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.ui.adapter
 * @ClassName : FriendListAdapter
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/7/18 10:10
 * @UpdateUser : yulu
 * @UpdateDate : 2023/7/18 10:10
 * @UpdateRemark : 更新说明
 */
class FriendListAdapter : RecyclerView.Adapter<FriendListAdapter.FriendListViewHolder>() {

    var list: Map<FriendInfo, User> = mapOf()
    var goUserDetail: (Long) -> Unit = { }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendListViewHolder {
        return FriendListViewHolder(parent)
    }

    override fun onBindViewHolder(holder: FriendListViewHolder, position: Int) {
        holder.setData(list.keys.toList()[position], list.values.toList()[position], goUserDetail)
    }

    override fun getItemCount(): Int {
        return list.size
    }


    class FriendListViewHolder(
        parent: ViewGroup,
        val binding: LayoutFriendListItemBinding = LayoutFriendListItemBinding.inflate(
            parent.context.layoutInflater(),
            parent,
            false
        )
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun setData(
            friend: FriendInfo,
            user: User,
            goUserDetail: (Long) -> Unit
        ) {
            binding.friendName.text = user.neck
            binding.friendItem.setOnClickListener {
                goUserDetail.invoke(friend.friendNumber)
            }
            GlideImageLoader().displayLocalFile(user.image, binding.friendIcon)
        }
    }

}