package com.example.myapplication.shareLifeUser.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myapplication.base.baseUi.BaseFragment
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentRegisterBinding
import com.example.myapplication.shareLifeUser.vm.RegisterViewModel

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

    lateinit var binding: FragmentRegisterBinding
    private val viewModel by viewModels<RegisterViewModel>()

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
            setBackgroundResource(R.color.color_FFFFFF)
            setBackListener() {
                findNavController().popBackStack()
            }
        }

        binding.registerBt.setOnClickListener {
            val number = binding.numberEdit.text.toString().trim()
            val pass = binding.passEdit.text.toString().trim()
            val pass2 = binding.passAgainEdit.text.toString().trim()
            viewModel.register(number, pass, pass2)
        }

        viewModel.registerType.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(R.id.goLoginFragment)
            }
        }

    }

}