package com.example.myapplication.view

import android.content.Context
import android.content.Intent
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.UriUtils
import com.example.myapplication.config.AppConfig
import com.example.myapplication.databinding.LayoutImageDisplayViewBinding
import com.example.myapplication.ui.adapter.ImageDisplayAdapter
import com.example.myapplication.useCase.PromptUseCase

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.view
 * @ClassName : ImageDisplayView
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/6/29 13:52
 * @UpdateUser : yulu
 * @UpdateDate : 2023/6/29 13:52
 * @UpdateRemark : 更新说明
 */
class ImageDisplayView(context: Context, attributeSet: AttributeSet? = null) :
    FrameLayout(context, attributeSet) {

    private var viewBinding: LayoutImageDisplayViewBinding
    private val imageDisplayAdapter = ImageDisplayAdapter()

    val list = mutableListOf<String>("")

    var addImageListener: () -> Unit = {}


    init {
        viewBinding =
            LayoutImageDisplayViewBinding.inflate(LayoutInflater.from(context), this, true)
        viewBinding.displayImageRecyclew.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            adapter = imageDisplayAdapter
        }

        imageDisplayAdapter.list = list

        imageDisplayAdapter.addImageListener = {
            //添加图片
            addImageListener.invoke()
        }

        imageDisplayAdapter.deleteImageListener = {
            //删除图片
            dialog(it)
        }
    }

    fun setData(data: MutableList<String>) {
        imageDisplayAdapter.list = data
        imageDisplayAdapter.notifyDataSetChanged()
    }

    fun addData(uri: String) {
        imageDisplayAdapter.list.add(uri)
        imageDisplayAdapter.notifyDataSetChanged()
    }

    fun getPhotos(): MutableList<String> {
        return list
    }

    fun dialog(position: Int) {
        PromptUseCase().removeImagePrompt {
            list.removeAt(position)
            imageDisplayAdapter.notifyDataSetChanged()
        }
    }


    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        //打开图片
        if (resultCode == AppCompatActivity.RESULT_OK && requestCode == AppConfig.IMAGE_OPEN) {
            if (data != null) {
                val uri = data.data
                val photoPath = UriUtils.uri2File(uri).absolutePath
                list.add(list.size - 1, photoPath)
                imageDisplayAdapter.list = getPhotos()
                imageDisplayAdapter.notifyDataSetChanged()
            }
        }
    }


}