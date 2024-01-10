package com.example.myapplication.ui.page.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.ctq.sphone.market.base.BaseFragment
import com.example.myapplication.databinding.FragmentLogViewBinding
import com.example.myapplication.util.readFileEachLineToTextView
import java.io.File

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName :
 * @Package : My Application
 * @ClassName : com.example.myapplication.ui.page.setting
 * @Description : 文件描述
 * @Author : Yulu
 * @CreateDate : 2024/1/10 14:11
 * @UpdateUser : Yulu
 * @UpdateDate : 2024/1/10 14:11
 * @UpdateRemark : 更新说明
 */
class LogViewFragment : BaseFragment() {

    private lateinit var binding: FragmentLogViewBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLogViewBinding.inflate(layoutInflater)
        initView()
        return binding.root
    }

    private fun initView() {
        val file = arguments?.getSerializable("logFile") as? File

        binding.include.headLayout.apply {
            file?.name?.let { setTitle(it) }
            setBackListener { findNavController().popBackStack() }
        }

        readFileEachLineToTextView(file ?: return, binding.logViewContent)
    }
}