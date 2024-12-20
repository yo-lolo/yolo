package com.example.myapplication.view

import android.content.Context
import android.content.Intent
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.UriUtils
import com.example.myapplication.DataManager
import com.example.myapplication.common.AppConfig
import com.example.myapplication.databinding.LayoutImageDisplayViewBinding
import com.example.myapplication.getTag
import com.example.myapplication.log.SpeedyLog
import com.example.myapplication.ui.adapter.ImageDisplayAdapter
import com.example.myapplication.util.PromptUtils

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
            layoutManager = DataManager.GridLayoutManagerNotScroll(4)
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

        imageDisplayAdapter.showImageListener = {
            PromptUtils().promptBigImage(it)
        }
    }

    fun setData(data: MutableList<String>) {
        imageDisplayAdapter.list = data
        notifyData()
    }

    fun addData(uri: String) {
        imageDisplayAdapter.list.add(uri)
        notifyData()
    }

    fun getPhotos(): MutableList<String> {
        return list
    }

    fun dialog(position: Int) {
        PromptUtils().removeImagePrompt {
            list.removeAt(position)
            notifyData()
        }
    }


    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        // 判断结果和请求码是否正确 再判断数据是否为空
        if (resultCode == AppCompatActivity.RESULT_OK && requestCode == AppConfig.IMAGE_OPEN) {
            if (data != null) {
                // 解析数据
                val uri = data.data
                val photoPath = UriUtils.uri2File(uri).absolutePath
                SpeedyLog.d(context.getTag(), "选中的图片路径为$photoPath")
                // 将图片路径添加到集合中
                list.add(list.size - 1, photoPath)
                // 配置适配器的数据
                imageDisplayAdapter.list = getPhotos()
                // 刷新
                notifyData()
            }
        }
    }

    /**
     * 适配器内容改变时 刷新每个item的内容
     */
    private fun notifyData() {
        imageDisplayAdapter.notifyDataSetChanged()
    }


}