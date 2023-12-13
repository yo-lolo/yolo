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
import com.example.myapplication.util.setText
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
    private var isMarket2Login: Boolean? = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        val view = binding.root
        isMarket2Login = arguments?.getBoolean("isMarket2Login")
        init(view)
        initAutoLogin()
        return view
    }

    fun init(view: View) {

        binding.registerBt.setOnClickListener {
            findNavController().navigate(R.id.goRegisterFragment)
        }
        binding.managerLoginBt.setOnClickListener {
            val (number, pass) = numberAndPass()
            viewModel.checkAdminLogin(number, pass)
        }
        binding.loginBt.setOnClickListener {
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

        binding.loginSetting.setOnClickListener {
            findNavController().navigate(R.id.goLoginSettingFragment)
        }

    }

    private fun numberAndPass(): Pair<String, String> {
        val number = binding.numberEdit.text.toString().trim()
        val pass = binding.passEdit.text.toString().trim()
        return number to pass
    }

    /**
     * 初始化自动登录
     */
    private fun initAutoLogin() {
        viewModel.isAutoLoginLiveData.observe(viewLifecycleOwner) {
            if (it && isMarket2Login == true) {
                viewModel.doAutoLogin()
            }
        }
        viewModel.isSaveUserPasswordLiveData.observe(viewLifecycleOwner) {
            val (num, pass) = if (it) viewModel.getUserNumAndPass() else "" to ""
            binding.numberEdit.setText(num)
            binding.passEdit.setText(pass)
        }
    }

}