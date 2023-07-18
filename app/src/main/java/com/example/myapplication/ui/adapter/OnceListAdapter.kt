package com.example.myapplication.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.ToastUtils
import com.example.myapplication.databinding.LayoutOnceListItemBinding
import com.example.myapplication.databinding.LayoutTwiceListItemBinding
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
class OnceListAdapter : RecyclerView.Adapter<OnceListAdapter.OnceListViewHolder>() {

    var list = listOf("yolo","ray","lisa","jessica","kay","v","jack","krystal","victory","luna","amber","a-lin")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnceListViewHolder {
        return OnceListViewHolder(parent)
    }

    override fun onBindViewHolder(holder: OnceListViewHolder, position: Int) {
        holder.setData(position, list[position])
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

        fun setData(position: Int, data: String) {
            binding.onceName.text = data
            binding.descText.text = "这里是对${data}的一段描述".repeat(2)
            binding.onceItem.setOnClickListener {
                ToastUtils.showShort("确定要删掉${data}吗?")
            }
        }
    }

}