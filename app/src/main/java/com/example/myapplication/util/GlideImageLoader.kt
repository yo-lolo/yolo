package com.example.myapplication.util

import android.content.Context
import android.net.Uri
import android.os.Build
import android.view.RoundedCorner
import android.widget.ImageView
import androidx.annotation.RequiresApi
import coil.request.DefaultRequestOptions
import coil.transform.RoundedCornersTransformation
import com.blankj.utilcode.util.UriUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.myapplication.DataManager
import com.example.myapplication.R
import com.youth.banner.loader.ImageLoader
import java.io.File

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

    var context = DataManager.context


    override fun displayImage(context: Context, path: Any?, imageView: ImageView) {
        Glide.with(context).load(path).error(R.mipmap.icon_empty).into(imageView)
    }

    fun displayImageWithRadius(path: Any?, imageView: ImageView) {
        Glide.with(context).load(path).error(R.mipmap.touxiang).into(imageView)
    }

    fun displayLocalFile(path: Any?, imageView: ImageView) {
        val localPath = Uri.fromFile(File(path as String))
        Glide.with(context).load(localPath).error(R.mipmap.touxiang).into(imageView)
    }

    fun displayImageError(path: Any?, imageView: ImageView) {
        val localPath = Uri.fromFile(File(path as String))
        Glide.with(context).load(localPath).error(R.mipmap.image_error).into(imageView)
    }

    fun displayImageUri(uri: Uri, imageView: ImageView) {
        val photoPath = UriUtils.uri2File(uri).absolutePath
        GlideImageLoader().displayLocalFile(photoPath, imageView)
    }


}