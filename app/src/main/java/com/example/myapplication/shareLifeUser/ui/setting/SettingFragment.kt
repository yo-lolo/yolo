package com.example.myapplication.shareLifeUser.ui.setting

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myapplication.base.baseUi.BaseFragment
import com.example.myapplication.R
import com.example.myapplication.chargeToastLogin
import com.example.myapplication.base.cache.MMKVManager
import com.example.myapplication.databinding.FragmentSettingBinding
import com.example.myapplication.isNotify
import com.example.myapplication.util.PromptUtils
import com.example.myapplication.shareLifeUser.vm.MineViewModel

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.page
 * @ClassName : SettingFragment
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/6/29 13:42
 * @UpdateUser : yulu
 * @UpdateDate : 2023/6/29 13:42
 * @UpdateRemark : 更新说明
 */
class SettingFragment : BaseFragment() {

    private lateinit var binding: FragmentSettingBinding
    val viewModel by viewModels<MineViewModel>()

    private var alertDialog //单选框
            : AlertDialog? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingBinding.inflate(inflater)
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
        binding.appMsg.setOnClickListener {
            startActivity(Intent().apply {
                action = "android.settings.APPLICATION_DETAILS_SETTINGS"
                data = Uri.parse("package:${context?.packageName}")
            })
        }

        binding.msgNotify.isChecked = isNotify()

        binding.modifyPassword.setOnClickListener {
            findNavController().navigate(R.id.goModifyPassFragment)
        }
        binding.msgNotify.setOnCheckedChangeListener { _, isChecked ->
            MMKVManager.isNotify = isChecked
        }
        binding.userProtocol.setOnClickListener {
            PAPFragment.goPAPFragment(
                findNavController(),
                "file:///android_asset/userProtocol.html"
            )
        }
        binding.privacyPolicy.setOnClickListener {
            PAPFragment.goPAPFragment(
                findNavController(),
                "file:///android_asset/privacyPolicy.html"
            )
        }
        binding.settingsAbout.setOnClickListener {
            findNavController().navigate(R.id.goAboutFragment)
        }
        binding.exitLogin.setOnClickListener {
            chargeToastLogin {
                PromptUtils().exitLoginPrompt {
                    viewModel.logout()
                    findNavController().navigate(R.id.goLoginFragment)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
    }

}