package com.example.myapplication.shareLifeUser.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.ToastUtils
import com.example.myapplication.databinding.LayoutTwiceListItemBinding
import com.example.myapplication.util.layoutInflater

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.ui.adapter
 * @ClassName : TwiceListAdapter
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/7/17 9:55
 * @UpdateUser : yulu
 * @UpdateDate : 2023/7/17 9:55
 * @UpdateRemark : 更新说明
 */
class TwiceListAdapter : RecyclerView.Adapter<TwiceListAdapter.TwiceListViewHolder>() {

    var list = listOf("林娜琏", "俞定延", "平井桃", "凑崎纱夏", "朴志效", "名井南", "金多贤", "孙彩瑛", "周子瑜")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TwiceListViewHolder {
        return TwiceListViewHolder(parent)
    }

    override fun onBindViewHolder(holder: TwiceListViewHolder, position: Int) {
        holder.setData(position, list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class TwiceListViewHolder(
        parent: ViewGroup,
        private val binding: LayoutTwiceListItemBinding = LayoutTwiceListItemBinding.inflate(
            parent.context.layoutInflater(),
            parent,
            false
        )
    ) : RecyclerView.ViewHolder(binding.root) {

        fun setData(position: Int, data: String) {
            binding.nameText.text = data
            binding.descText.text = "这里是对${data}的一段描述".repeat(3)
            binding.detailButton.setOnClickListener {
                ToastUtils.showShort("详情${position + 1}")
            }
        }

    }
}