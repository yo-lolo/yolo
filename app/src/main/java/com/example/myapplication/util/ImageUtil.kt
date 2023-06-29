package com.example.myapplication.util

import android.widget.ImageView
import coil.load
import coil.request.ImageRequest
import coil.transform.RoundedCornersTransformation
import com.example.myapplication.R

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.util
 * @ClassName : ImageUtil
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/6/29 13:55
 * @UpdateUser : yulu
 * @UpdateDate : 2023/6/29 13:55
 * @UpdateRemark : 更新说明
 */
object ImageUtil {

    fun displayUserIcon(
        imageView: ImageView,
        url: String,
    ) {
        displayImageView(imageView, url) {
            transformations(RoundedCornersTransformation(3 * 17f))
            placeholder(R.mipmap.mine_touxiang_icon)
            error(R.mipmap.mine_touxiang_icon)
            fallback(R.mipmap.mine_touxiang_icon)
        }
    }

    fun displayAppIcon(
        imageView: ImageView,
        url: String,
    ) {
        displayImageView(imageView, url){
            transformations(RoundedCornersTransformation(3 * 10f))
        }
    }

    fun displayImageView(
        imageView: ImageView,
        url: String,
        builder: ImageRequest.Builder.() -> Unit = {}
    ) {
        imageView.load(data = url, builder = builder)
    }
}