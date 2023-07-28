package com.example.myapplication.ui.page.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.findNavController
import com.blankj.utilcode.util.ToastUtils
import com.ctq.sphone.market.base.BaseFragment
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentChatBinding
import com.example.myapplication.databinding.FragmentPostCommentBinding

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.ui.page.home
 * @ClassName : PostCommentFragment
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/7/28 15:33
 * @UpdateUser : yulu
 * @UpdateDate : 2023/7/28 15:33
 * @UpdateRemark : 更新说明
 */
class PostCommentFragment : BaseFragment() {

    private lateinit var binding: FragmentPostCommentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPostCommentBinding.inflate(layoutInflater)
        initView()
        return binding.root
    }

    fun initView() {
        binding.replyBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.postButton.setOnClickListener {
            ToastUtils.showShort("点我干嘛")
        }
        binding.replyContentEdit.doOnTextChanged { text, _, _, _ ->
            if (text!!.isNotEmpty()) {
                binding.postButton.apply {
                    setTextColor(resources.getColor(R.color.color_theme))
                    isClickable = true
                }
            } else {
                binding.postButton.apply {
                    setTextColor(resources.getColor(R.color.color_lower_blue))
                    isClickable = false
                }
            }
        }
    }
}