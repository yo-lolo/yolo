package com.example.myapplication.ui.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.database.entity.FriendInfo
import com.example.myapplication.databinding.LayoutNewFriendsItemBinding
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

    var list: List<FriendInfo> = listOf()
    var goUserDetail: (Long) -> Unit = { }
    var agreeFriendListener: (Long, Long) -> Unit = { id, number -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendListViewHolder {
        return FriendListViewHolder(parent)
    }

    override fun onBindViewHolder(holder: FriendListViewHolder, position: Int) {
        holder.setData(position, list[position], goUserDetail, agreeFriendListener)
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
            goUserDetail: (Long) -> Unit,
            agreeFriendListener: (Long, Long) -> Unit,
        ) {
            binding.friendName.text = friend.number.toString()
            if (friend.tag == 0) {
                binding.agreeFriend.visibility = View.VISIBLE
                binding.alreadyCheck.visibility = View.GONE
            } else if (friend.tag == 1) {
                binding.agreeFriend.visibility = View.GONE
                binding.alreadyCheck.visibility = View.VISIBLE
                binding.alreadyCheck.text = "已添加"
            } else {
                binding.agreeFriend.visibility = View.GONE
                binding.alreadyCheck.visibility = View.VISIBLE
                binding.alreadyCheck.text = "已拒绝"
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