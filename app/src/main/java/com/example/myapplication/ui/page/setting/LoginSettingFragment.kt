package com.example.myapplication.ui.page.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ctq.sphone.market.base.BaseFragment
import com.example.myapplication.R
import com.example.myapplication.data.MMKVManager
import com.example.myapplication.databinding.FragmentLoginSettingBinding

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.page
 * @ClassName : SettingFragment
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/10/20 16:04
 * @UpdateUser : yulu
 * @UpdateDate : 2023/10/20 16:04
 * @UpdateRemark : 更新说明
 */
class LoginSettingFragment : BaseFragment() {

    private lateinit var binding: FragmentLoginSettingBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginSettingBinding.inflate(inflater)
        val view = binding.root
        initView(view)
        return view
    }

    private fun initView(view: View) {
        binding.include.headLayout.apply {
            setTitle("设置")
            setBackListener {
                findNavController().popBackStack()
            }
        }

        binding.autoLogin.isChecked = MMKVManager.isAutoLogin
        binding.saveLoginMess.isChecked = MMKVManager.isSavePass

        binding.autoLogin.setOnCheckedChangeListener { _, isChecked ->
            MMKVManager.isAutoLogin = isChecked
        }
        binding.saveLoginMess.setOnCheckedChangeListener { _, isChecked ->
            MMKVManager.isSavePass = isChecked
        }
        binding.logManage.setOnClickListener {
            findNavController().navigate(R.id.goLogManageFragment)
        }

    }

}