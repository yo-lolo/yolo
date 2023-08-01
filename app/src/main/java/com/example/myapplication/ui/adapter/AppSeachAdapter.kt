package com.example.myapplication.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.util.ImageUtil

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.ui.adapter
 * @ClassName : AppSeachAdapter
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/6/29 16:25
 * @UpdateUser : yulu
 * @UpdateDate : 2023/6/29 16:25
 * @UpdateRemark : 更新说明
 */
class AppSeachAdapter : RecyclerView.Adapter<AppSeachAdapter.AppViewHodler>() {
    var list = listOf<String>()

    var fragmentLifecycle: Lifecycle? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppViewHodler {
        return AppViewHodler(parent)
    }

    override fun onBindViewHolder(holder: AppViewHodler, position: Int) {
        holder.setData(fragmentLifecycle!!)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class AppViewHodler(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.layout_search_app_list_item, parent, false
        )
    ) {
        val iconImage: ImageView = itemView.findViewById(R.id.icon_image)

        fun setData(
            fragmentLifecycle: Lifecycle,
        ) {
            Log.e("abc", "setData: ")
            ImageUtil.displayAppIcon(iconImage, "")


            itemView.setOnClickListener {

            }
        }

    }

}