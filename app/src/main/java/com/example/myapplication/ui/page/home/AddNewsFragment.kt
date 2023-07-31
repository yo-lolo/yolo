package com.example.myapplication.ui.page.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ctq.sphone.market.base.BaseFragment
import com.example.myapplication.databinding.FragmentAddNewsBinding

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
    }
}