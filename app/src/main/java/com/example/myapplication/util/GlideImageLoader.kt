package com.example.myapplication.util

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.youth.banner.loader.ImageLoader

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.util
 * @ClassName : GlideImageLoader
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/7/17 14:21
 * @UpdateUser : yulu
 * @UpdateDate : 2023/7/17 14:21
 * @UpdateRemark : 更新说明
 */


/**
 * 自定义的图片加载器
 */
class GlideImageLoader : ImageLoader() {
    override fun displayImage(context: Context?, path: Any?, imageView: ImageView?) {
        Glide.with(context).load(path).error(R.mipmap.icon_empty).into(imageView)
    }
}