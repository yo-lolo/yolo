package com.example.myapplication.ui.page.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.blankj.utilcode.util.ActivityUtils
import com.ctq.sphone.market.base.BaseFragment
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentLoginBinding
import com.example.myapplication.admin.ManageActivity
import com.example.myapplication.vm.LoginViewModel

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

    lateinit var binding: FragmentLoginBinding
    private val viewModel by viewModels<LoginViewModel>()

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

    fun init(view: View) {

        binding.registerBt.setOnClickListener {
            findNavController().navigate(R.id.goRegisterFragment)
        }
        binding.managerLoginBt.setOnClickListener {
            //TODO 管理员登录信息验证
            val (number, pass) = numberAndPass()
            viewModel.checkAdminLogin(number, pass)
        }
        binding.loginBt.setOnClickListener {
            //Todo 用户信息初始化
            val (number, pass) = numberAndPass()
            viewModel.checkLogin(number, pass)
        }

        viewModel.adminLoginType.observe(viewLifecycleOwner) {
            if (it) {
                ActivityUtils.startActivity(ManageActivity::class.java)
            }
        }

        viewModel.loginType.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(R.id.goHomeFragment)
            }
        }

    }

    private fun numberAndPass(): Pair<String, String> {
        val number = binding.numberEdit.text.toString().trim()
        val pass = binding.passEdit.text.toString().trim()
        return number to pass
    }

}