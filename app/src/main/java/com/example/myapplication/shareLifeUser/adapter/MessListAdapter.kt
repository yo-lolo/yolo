package com.example.myapplication.shareLifeUser.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.ToastUtils
import com.example.myapplication.bean.MessData
import com.example.myapplication.databinding.LayoutMessListItemBinding
import com.example.myapplication.util.GlideImageLoader
import com.example.myapplication.util.TimeUtil
import com.example.myapplication.util.layoutInflater

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.ui.adapter
 * @ClassName : MessListAdapter
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/7/31 14:35
 * @UpdateUser : yulu
 * @UpdateDate : 2023/7/31 14:35
 * @UpdateRemark : 更新说明
 */
class MessListAdapter : RecyclerView.Adapter<MessListAdapter.FriendListViewHolder>() {

    var list: Map<Long, MessData> = mapOf()
    var goChatListener: (Long) -> Unit = { }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendListViewHolder {
        return FriendListViewHolder(parent)
    }

    override fun onBindViewHolder(holder: FriendListViewHolder, position: Int) {
        holder.setData(
            list.keys.toList()[position],
            list.values.toList()[position],
            goChatListener
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }


    class FriendListViewHolder(
        parent: ViewGroup,
        val binding: LayoutMessListItemBinding = LayoutMessListItemBinding.inflate(
            parent.context.layoutInflater(),
            parent,
            false
        )
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun setData(
            number: Long,
            messData: MessData,
            goChatListener: (Long) -> Unit
        ) {
            val chatInfo = messData.newChatInfo
            val friendUser = messData.friendUser
            binding.friendName.text = friendUser.neck
            binding.friendLastMess.text = chatInfo.content
            binding.lastMessTime.text =
                TimeUtil.getFriendlyTimeSpanByNow(chatInfo.time)
            GlideImageLoader().displayLocalFile(friendUser.image, binding.friendIcon)
            binding.friendItem.setOnClickListener {
                goChatListener.invoke(number)
            }
            binding.deleteItem.setOnClickListener {
                ToastUtils.showShort("删除")
            }
            binding.topItem.setOnClickListener {
                ToastUtils.showShort("置顶")
            }
            binding.unreadItem.setOnClickListener {
                ToastUtils.showShort("标为未读")
            }
        }
    }

}