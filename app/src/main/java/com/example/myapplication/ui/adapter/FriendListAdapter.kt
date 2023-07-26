package com.example.myapplication.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.ToastUtils
import com.example.myapplication.databinding.LayoutFriendListItemBinding
import com.example.myapplication.util.layoutInflater

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.ui.adapter
 * @ClassName : OnceListAdapter
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/7/18 10:10
 * @UpdateUser : yulu
 * @UpdateDate : 2023/7/18 10:10
 * @UpdateRemark : 更新说明
 */
class FriendListAdapter : RecyclerView.Adapter<FriendListAdapter.FriendListViewHolder>() {

    var list = mutableListOf(
        "不开兴和没头脑",
        "LZ公司交流群",
        "Yolo",
        "三人行",
        "MXM平台开发群",
        "BU1知识群",
        "守正物业INTEGRITY SERVICE",
        "魔仙堡",
        "舅妈",
        "中电信干饭群"
    )
    var deleteListener: (Int, String) -> Unit = { _, _ -> }
    var goChatListener: (String) -> Unit = { }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendListViewHolder {
        return FriendListViewHolder(parent)
    }

    override fun onBindViewHolder(holder: FriendListViewHolder, position: Int) {
        holder.setData(position, list[position], deleteListener, goChatListener)
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
            position: Int,
            data: String,
            deleteListener: (Int, String) -> Unit,
            goChatListener: (String) -> Unit
        ) {
            binding.friendName.text = data
            binding.friendLastMess.text = "这里是对${data}的一段描述".repeat(2)
            binding.lastMessTime.text = "18:00"
            binding.friendItem.setOnLongClickListener {
                ToastUtils.showShort("你想干嘛")
                deleteListener.invoke(position, data)
                true
            }
            binding.friendItem.setOnClickListener {
                goChatListener.invoke(data)
            }
        }
    }

}