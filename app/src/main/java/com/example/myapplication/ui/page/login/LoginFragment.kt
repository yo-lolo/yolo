package com.example.myapplication.ui.page.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.blankj.utilcode.util.ActivityUtils
import com.ctq.sphone.market.base.BaseFragment
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentLoginBinding
import com.example.myapplication.ui.page.manager.ManageActivity

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.ui.page.login
 * @ClassName : LoginFragment
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/7/7 13:49
 * @UpdateUser : yulu
 * @UpdateDate : 2023/7/7 13:49
 * @UpdateRemark : 更新说明
 */
class LoginFragment : BaseFragment() {

    lateinit var binding : FragmentLoginBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        val view = binding.root
        init(view)
        return view
    }

    fun init(view: View){

        binding.registerBt.setOnClickListener {
            findNavController().navigate(R.id.goRegisterFragment)
        }
        binding.managerLoginBt.setOnClickListener {
            //
            ActivityUtils.startActivity(ManageActivity::class.java)
        }
        binding.loginBt.setOnClickListener {
            //Todo 验证登录信息 用户信息初始化
            findNavController().navigate(R.id.goHomeFragment)
        }

    }

}