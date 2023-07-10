package com.example.myapplication.ui.page.manager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ctq.sphone.market.base.BaseFragment
import com.example.myapplication.databinding.FragmentManagerBinding

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.ui.page.manager
 * @ClassName : ManagerFragment
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/7/10 15:34
 * @UpdateUser : yulu
 * @UpdateDate : 2023/7/10 15:34
 * @UpdateRemark : 更新说明
 */
class ManagerFragment : BaseFragment() {

    private lateinit var binding: FragmentManagerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentManagerBinding.inflate(layoutInflater)
        val view = binding.root
        initView(view)
        return view
    }

    private fun initView(view: View) {

    }
}