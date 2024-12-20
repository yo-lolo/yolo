package com.example.myapplication.shareLifeUser.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.LayoutOnceListItemBinding
import com.example.myapplication.util.layoutInflater
import java.util.Random

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
class OnceListAdapter : RecyclerView.Adapter<OnceListAdapter.OnceListViewHolder>() {

    var list = mutableListOf(
        "yolo",
        "ray",
        "lisa",
        "jessica",
        "kay",
        "v",
        "jack",
        "krystal",
        "victory",
        "luna",
        "amber",
        "a-lin"
    )
    var deleteListener: (Int, String) -> Unit = { _, _ -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnceListViewHolder {
        return OnceListViewHolder(parent)
    }

    override fun onBindViewHolder(holder: OnceListViewHolder, position: Int) {
        holder.setData(position, list[position], deleteListener)
    }

    override fun getItemCount(): Int {
        return list.size
    }


    class OnceListViewHolder(
        parent: ViewGroup,
        val binding: LayoutOnceListItemBinding = LayoutOnceListItemBinding.inflate(
            parent.context.layoutInflater(),
            parent,
            false
        )
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun setData(
            position: Int,
            data: String,
            deleteListener: (Int, String) -> Unit
        ) {
            binding.onceName.text = data
            binding.descText.text = "这里是对${data}的一段描述".repeat(Random().nextInt(6) + 1)
            binding.onceItem.setOnLongClickListener {
                deleteListener.invoke(position, data)
                true
            }
        }
    }

}