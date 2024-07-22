package com.example.myapplication.ui.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.common.AppConfig
import com.example.myapplication.database.entity.FriendInfo
import com.example.myapplication.database.entity.User
import com.example.myapplication.databinding.LayoutNewFriendsItemBinding
import com.example.myapplication.util.GlideImageLoader
import com.example.myapplication.util.layoutInflater

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.ui.adapter
 * @ClassName : NewFriendListAdapter
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/7/31 11:36
 * @UpdateUser : yulu
 * @UpdateDate : 2023/7/31 11:36
 * @UpdateRemark : 更新说明
 */
class NewFriendListAdapter : RecyclerView.Adapter<NewFriendListAdapter.FriendListViewHolder>() {

    var list: Map<FriendInfo, User> = mapOf()
    var goUserDetail: (Long) -> Unit = {}
    var agreeFriendListener: (Long, Long) -> Unit = { id, number -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendListViewHolder {
        return FriendListViewHolder(parent)
    }

    override fun onBindViewHolder(holder: FriendListViewHolder, position: Int) {
        holder.setData(
            position,
            list.keys.toList()[position],
            list.values.toList()[position],
            goUserDetail,
            agreeFriendListener
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }


    class FriendListViewHolder(
        parent: ViewGroup,
        val binding: LayoutNewFriendsItemBinding = LayoutNewFriendsItemBinding.inflate(
            parent.context.layoutInflater(),
            parent,
            false
        )
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun setData(
            position: Int,
            friend: FriendInfo,
            user: User,
            goUserDetail: (Long) -> Unit,
            agreeFriendListener: (Long, Long) -> Unit,
        ) {
            binding.friendName.text = user.neck
            GlideImageLoader().displayLocalFile(user.image, binding.friendIcon)
            when (friend.tag) {
                AppConfig.NOT_FRIEND -> {
                    binding.agreeFriend.visibility = View.VISIBLE
                    binding.alreadyCheck.visibility = View.GONE
                }
                AppConfig.IS_FRIEND -> {
                    binding.agreeFriend.visibility = View.GONE
                    binding.alreadyCheck.visibility = View.VISIBLE
                    binding.alreadyCheck.text = "已添加"
                }

            }

            binding.friendItem.setOnClickListener {
                goUserDetail.invoke(friend.number)
            }

            binding.agreeFriend.setOnClickListener {
                agreeFriendListener.invoke(friend.id, friend.number)
            }
        }
    }

}