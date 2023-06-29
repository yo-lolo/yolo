package com.example.myapplication.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.transform.RoundedCornersTransformation
import coil.util.CoilUtils
import com.example.myapplication.R
import com.example.myapplication.databinding.LayoutImageItemBinding
import com.example.myapplication.util.ImageUtil

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.ui.adapter
 * @ClassName : ImageDisplayAdapter
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/6/29 13:53
 * @UpdateUser : yulu
 * @UpdateDate : 2023/6/29 13:53
 * @UpdateRemark : 更新说明
 */
class ImageDisplayAdapter : RecyclerView.Adapter<ImageDisplayAdapter.ImageDisplayViewHolder>() {

    var list = mutableListOf<String>("")

    var deleteImageListener: (Int) -> Unit = {}

    var showImageListener: (String) -> Unit = {}

    var addImageListener: () -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageDisplayViewHolder {
        return ImageDisplayViewHolder(parent)
    }

    override fun onBindViewHolder(holder: ImageDisplayViewHolder, position: Int) {
        holder.setData(
            position,
            list[position],
            deleteImageListener,
            showImageListener,
            addImageListener
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ImageDisplayViewHolder(
        parent: ViewGroup,
        val binding: LayoutImageItemBinding = LayoutImageItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    ) : RecyclerView.ViewHolder(binding.root) {
        fun setData(
            position: Int,
            uri: String,
            deleteImageListener: (Int) -> Unit,
            showImageListener: (String) -> Unit = {},
            addImageListener: () -> Unit = {}
        ) {
            //判断是否为空 不为空则加载图片 ImageUtil
            var feedbackImage = itemView.findViewById<ImageView>(R.id.feedback_image)
            var deleteImage = itemView.findViewById<ImageView>(R.id.delete_image)

            feedbackImage.apply {
                setBackgroundResource(0)
                setImageResource(0)
                setImageDrawable(null)
            }
            CoilUtils.dispose(feedbackImage)

            if (uri == "") {

                deleteImage.visibility = View.GONE
                feedbackImage.apply {
                    setBackgroundResource(R.drawable.app_icon_image_bg)
                    setImageResource(R.mipmap.icon_image_add)
                    scaleType = ImageView.ScaleType.CENTER_INSIDE
                }

                itemView.setOnClickListener {
                    addImageListener.invoke()
                }
            } else {
                deleteImage.visibility = View.VISIBLE
                ImageUtil.displayImageView(feedbackImage, uri) {
                    transformations(RoundedCornersTransformation(3 * 7f))
                }
                feedbackImage.scaleType = ImageView.ScaleType.CENTER_CROP
                deleteImage.setOnClickListener {
                    deleteImageListener.invoke(position)
                    true
                }
                itemView.setOnClickListener {
                    showImageListener.invoke(uri)
                }
            }
        }
    }
}