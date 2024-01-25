package com.example.myapplication.ui.page.feedback

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ctq.sphone.market.base.BaseFragment
import com.example.myapplication.R
import com.example.myapplication.chargeToastLogin
import com.example.myapplication.config.AppConfig
import com.example.myapplication.databinding.FragmentFeedbackBinding
import com.example.myapplication.vm.FeedbackViewModel

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.ui.page.feedback
 * @ClassName : FeedbackFragment
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/6/29 13:45
 * @UpdateUser : yulu
 * @UpdateDate : 2023/6/29 13:45
 * @UpdateRemark : 更新说明
 */
class FeedbackFragment : BaseFragment() {

    private lateinit var binding: FragmentFeedbackBinding

    private val feedbackViewModel by viewModels<FeedbackViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFeedbackBinding.inflate(layoutInflater)
        val view = binding.root
        initView(view)
        return view
    }

    private fun initView(view: View) {

        binding.include.headLayout.apply {
            setBackgroundResource(R.color.color_activity_bg)
            setBackListener() {
                findNavController().popBackStack()
            }
            setTitle("问题反馈")
        }

        binding.feedbackPDetail.doOnTextChanged { text, start, count, after ->
            binding.pDetailCount.text = "${text!!.length}/200"
        }

        binding.imageDisplayView.apply {
            addImageListener = {
                openPhoto()
            }
        }

        binding.feedbackSubmit.setOnClickListener {
            val pictureItems = binding.imageDisplayView.getPhotos().filter { !it.isNullOrBlank() }
            val detail = binding.feedbackPDetail.text.toString().trim()
            chargeToastLogin {
                feedbackViewModel.onSubmit(pictureItems, detail)
            }
        }

        binding.apply {
            qualityProblem.setOnClickListener {
                feedbackViewModel.typeChange(it.tag as String)
            }
            useProblem.setOnClickListener {
                feedbackViewModel.typeChange(it.tag as String)
            }
            otherProblem.setOnClickListener {
                feedbackViewModel.typeChange(it.tag as String)
            }
        }

        feedbackViewModel.type.observe(viewLifecycleOwner) {
            changeStyle(it)
        }

        feedbackViewModel.commentSuccess.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().popBackStack()
            }
        }

    }

    /**
     * 实现单选问题类型，点击改变组件样式
     */
    private fun changeStyle(data: String) {
        binding.qualityProblem.apply {
            setBackgroundResource(if (data == this.tag) R.drawable.type_button_true_bg else R.drawable.type_button_false_bg)
            setTextColor(resources.getColor(if (data == this.tag) R.color.color_e7e7e7 else R.color.color_3c3c3c))
        }
        binding.useProblem.apply {
            setBackgroundResource(if (data == this.tag) R.drawable.type_button_true_bg else R.drawable.type_button_false_bg)
            setTextColor(resources.getColor(if (data == this.tag) R.color.color_e7e7e7 else R.color.color_3c3c3c))
        }
        binding.otherProblem.apply {
            setBackgroundResource(if (data == this.tag) R.drawable.type_button_true_bg else R.drawable.type_button_false_bg)
            setTextColor(resources.getColor(if (data == this.tag) R.color.color_e7e7e7 else R.color.color_3c3c3c))
        }
    }

    /**
     * 打开相册选择图片
     * 调用startActivityForResult方法向系统选择器请求数据 第二个参数是请求码
     */
    private fun openPhoto() {
        if (binding.imageDisplayView.getPhotos().size > 5) {
            Toast.makeText(context, "最多添加5张图片", Toast.LENGTH_SHORT).show()
        } else {
            val intent = Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )
            startActivityForResult(intent, AppConfig.IMAGE_OPEN)
        }
    }

    /**
     * 解析系统选择器返回的结果
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        binding.imageDisplayView.onActivityResult(requestCode, resultCode, data)
    }


}