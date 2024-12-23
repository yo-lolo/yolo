package com.example.myapplication.shareLifeUser.ui.home

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.blankj.utilcode.util.UriUtils
import com.example.myapplication.base.baseUi.BaseFragment
import com.example.myapplication.common.AppConfig
import com.example.myapplication.databinding.FragmentAddNewsBinding
import com.example.myapplication.util.GlideImageLoader
import com.example.myapplication.shareLifeUser.vm.HomeViewModel

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.ui.page.home
 * @ClassName : AddNewsFragment
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/7/31 10:33
 * @UpdateUser : yulu
 * @UpdateDate : 2023/7/31 10:33
 * @UpdateRemark : 更新说明
 */
class AddNewsFragment : BaseFragment() {

    private lateinit var binding: FragmentAddNewsBinding
    val viewModel by viewModels<HomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddNewsBinding.inflate(layoutInflater)
        initView()
        return binding.root
    }

    private fun initView() {
        binding.include.headLayout.apply {
            setTitle("写文章")
            setBackListener {
                findNavController().popBackStack()
            }
        }

        binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            val tagButton =
                binding.root.findViewById<RadioButton>(binding.radioGroup.checkedRadioButtonId)
            var tag = tagButton.text.toString().trim()
            viewModel.refreshTag(tag)
        }

        binding.newsSubmit.setOnClickListener {
            val title = binding.newsTitle.text.toString().trim()
            val content = binding.newsContent.text.toString().trim()
            viewModel.onSubmit(title, content)
        }

        binding.addImage.setOnClickListener {
            openPhoto()
        }

        viewModel.newsPostState.observe(viewLifecycleOwner) {
            if (it) findNavController().popBackStack()
        }

    }

    /**
     * 打开相册选择图片
     * 调用startActivityForResult方法向系统选择器请求数据 第二个参数是请求码
     */
    private fun openPhoto() {
        val intent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        startActivityForResult(intent, AppConfig.NEWS_IMAGE_OPEN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        // 判断结果和请求码是否正确 再判断数据是否为空
        if (resultCode == AppCompatActivity.RESULT_OK && requestCode == AppConfig.NEWS_IMAGE_OPEN) {
            if (data != null) {
                // 解析数据
                val uri = data.data
                val photoPath = UriUtils.uri2File(uri).absolutePath
                viewModel.refreshPath(photoPath)
                GlideImageLoader().displayLocalFile(photoPath, binding.imageShow)
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}