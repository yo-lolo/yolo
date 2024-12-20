package com.example.myapplication.shareLifeAdmin.adpter

import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.DataManager
import com.example.myapplication.base.database.entity.FeedbackInfo
import com.example.myapplication.databinding.LayoutManageFeedbacksItemBinding
import com.example.myapplication.util.JsonUtil
import com.example.myapplication.util.TimeUtil
import com.example.myapplication.util.layoutInflater
import com.google.gson.reflect.TypeToken
import java.util.ArrayList

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.admin.adpter
 * @ClassName : FeedbacksAdapter
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/8/1 13:40
 * @UpdateUser : yulu
 * @UpdateDate : 2023/8/1 13:40
 * @UpdateRemark : 更新说明
 */
class AdminFeedbacksAdapter : RecyclerView.Adapter<AdminFeedbacksAdapter.FeedbacksViewHolder>() {

    var list: List<FeedbackInfo> = listOf()
    var showBigImageListener: (String) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedbacksViewHolder {
        return FeedbacksViewHolder(parent)
    }

    override fun onBindViewHolder(holder: FeedbacksViewHolder, position: Int) {
        holder.setData(list[position], showBigImageListener)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class FeedbacksViewHolder(
        parent: ViewGroup,
        val binding: LayoutManageFeedbacksItemBinding = LayoutManageFeedbacksItemBinding.inflate(
            parent.context.layoutInflater(),
            parent,
            false
        )
    ) : RecyclerView.ViewHolder(binding.root) {
        private var imagesAdapter = ImagesAdapter()

        fun setData(feedbackInfo: FeedbackInfo, showBigImageListener: (String) -> Unit) {
            binding.feedbackNumber.text = "账号：${feedbackInfo.phoneNumber.toString()}"
            binding.feedbackDesc.text = "描述：${feedbackInfo.desc}"
            binding.feedbackTime.text = TimeUtil.millis2String(feedbackInfo.time)

            val type = object : TypeToken<ArrayList<String>>() {}.type
            val pictureItems = JsonUtil.fromJson<List<String>>(feedbackInfo.pictureItems, type)

            binding.feedbackImages.apply {
                layoutManager =
                    LinearLayoutManager(DataManager.context, RecyclerView.HORIZONTAL, false)
                adapter = imagesAdapter
            }

            imagesAdapter.apply {
                list = pictureItems!!
                notifyDataSetChanged()
            }

            imagesAdapter.showBigImageListener = {
                showBigImageListener.invoke(it)
            }
        }
    }
}

