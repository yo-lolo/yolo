package com.example.myapplication.ui.page.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ctq.sphone.market.base.BaseFragment
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentRegisterBinding

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.ui.page.register
 * @ClassName : RegisterFragment
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/7/7 13:49
 * @UpdateUser : yulu
 * @UpdateDate : 2023/7/7 13:49
 * @UpdateRemark : 更新说明
 */
class RegisterFragment : BaseFragment() {

    lateinit var binding : FragmentRegisterBinding
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

    fun init(view: View) {
        binding.includeHead.headLayout.apply {
            setTitle("注册")
            setBackgroundResource(R.color.app_color)
            setBackListener() {
                findNavController().popBackStack()
            }
        }
    }

}