package com.example.myapplication.ui.page.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ctq.sphone.market.base.BaseFragment
import com.example.myapplication.databinding.FragmentModifyPassBinding
import com.example.myapplication.util.getEditText
import com.example.myapplication.vm.MineViewModel

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName :
 * @Package : My Application
 * @ClassName : com.example.myapplication.ui.page.setting
 * @Description : 文件描述
 * @Author : Yulu
 * @CreateDate : 2023/10/20 11:21
 * @UpdateUser : Yulu
 * @UpdateDate : 2023/10/20 11:21
 * @UpdateRemark : 更新说明
 */
class ModifyPassFragment : BaseFragment() {

    private lateinit var viewBinding: FragmentModifyPassBinding
    val viewModel by viewModels<MineViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentModifyPassBinding.inflate(layoutInflater)
        initView()
        return viewBinding.root
    }

    fun initView() {
        viewBinding.include.headLayout.apply {
            setTitle("修改密码")
            setBackListener {
                findNavController().popBackStack()
            }
        }

        viewBinding.sureModifyPwd.setOnClickListener {
            val oldPwd = viewBinding.inputOldPwd.getEditText()
            val newPwd = viewBinding.inputNewPwd.getEditText()
            val newPwdAgain = viewBinding.inputNewPwdAgain.getEditText()
            viewModel.doModifyPass(oldPwd, newPwd, newPwdAgain)
        }

        viewModel.doModifyType.observe(viewLifecycleOwner) {
            if (it)
                findNavController().popBackStack()
        }
    }

}