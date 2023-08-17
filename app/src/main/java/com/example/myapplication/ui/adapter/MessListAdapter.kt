package com.example.myapplication.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.LayoutMessListItemBinding
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

    var list: Map<Long, List<String>> = mapOf()
    var goChatListener: (Long) -> Unit = { }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendListViewHolder {
        return FriendListViewHolder(parent)
    }

    override fun onBindViewHolder(holder: FriendListViewHolder, position: Int) {
        holder.setData(
            list.keys.toList()[position],
            list[list.keys.toList()[position]]!!,
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
            contentAndTime: List<String>,
            goChatListener: (Long) -> Unit
        ) {
            binding.friendName.text = number.toString()
            binding.friendLastMess.text = contentAndTime[0]
            binding.lastMessTime.text = TimeUtil.getFriendlyTimeSpanByNow(contentAndTime[1].toLong())
            binding.friendItem.setOnLongClickListener {
                // TODO
                true
            }
            binding.friendItem.setOnClickListener {
                goChatListener.invoke(number)
            }
        }
    }

}