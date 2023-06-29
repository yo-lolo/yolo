package com.example.myapplication.ui.page.setting

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ctq.sphone.market.base.BaseFragment
import com.example.myapplication.databinding.FragmentSettingBinding

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
        binding.autoUpdateApk.setOnClickListener {

        }

        binding.downloadRemindSetting.setOnClickListener {

        }
        binding.updateNotificationSwitch.setOnCheckedChangeListener { _, isChecked ->

        }
        binding.msgNotificationSwitch.setOnCheckedChangeListener { _, isChecked ->

        }
        binding.checkoutUpdate.setOnClickListener {
            //TODO 检查更新
        }
        binding.settingsAbout.setOnClickListener {
            //TODO 关于
        }
    }

    companion object {
        fun goSettingFragment(): Fragment {
            return SettingFragment()
        }
    }

    override fun onResume() {
        super.onResume()
        initData()
    }

    private fun initData() {

    }

}