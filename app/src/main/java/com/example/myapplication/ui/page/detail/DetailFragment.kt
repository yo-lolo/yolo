package com.example.myapplication.ui.page.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ctq.sphone.market.base.BaseFragment
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentLoginBinding
import com.example.myapplication.databinding.FragmentRegisterBinding

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.ui.page.detail
 * @ClassName : DetailFragment
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/6/29 15:52
 * @UpdateUser : yulu
 * @UpdateDate : 2023/6/29 15:52
 * @UpdateRemark : 更新说明
 */
class DetailFragment : BaseFragment() {

    lateinit var binding: FragmentRegisterBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        init(view)
        return view
    }

    fun init(view: View){

    }

}