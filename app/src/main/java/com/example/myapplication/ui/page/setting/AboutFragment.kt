package com.example.myapplication.ui.page.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ctq.sphone.market.base.BaseFragment
import com.example.myapplication.config.AppConfig
import com.example.myapplication.databinding.FragmentAboutBinding

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName :
 * @Package : My Application
 * @ClassName : com.example.myapplication.ui.page.setting
 * @Description : 文件描述
 * @Author : Yulu
 * @CreateDate : 2023/10/19 14:43
 * @UpdateUser : Yulu
 * @UpdateDate : 2023/10/19 14:43
 * @UpdateRemark : 更新说明
 */
class AboutFragment : BaseFragment() {

    private lateinit var viewBinding: FragmentAboutBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentAboutBinding.inflate(layoutInflater)
        val view = viewBinding.root
        initView(view)
        return view
    }

    fun initView(view: View) {
        viewBinding.include.headLayout.apply {
            setTitle("关于")
            setBackListener {
                findNavController().popBackStack()
            }
        }

        viewBinding.appVersion.text = "V" + AppConfig.VERSION_NAME

        viewBinding.appName.text = AppConfig.APP_NAME

        viewBinding.appPackgeName.text = AppConfig.PACKAGE_NAME
    }

}