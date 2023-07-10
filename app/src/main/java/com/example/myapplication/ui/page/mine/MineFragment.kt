package com.example.myapplication.ui.page.mine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.ctq.sphone.market.base.BaseFragment
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.databinding.FragmentMineBinding

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.ui.page.mine
 * @ClassName : MineFragment
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/6/29 13:44
 * @UpdateUser : yulu
 * @UpdateDate : 2023/6/29 13:44
 * @UpdateRemark : 更新说明
 */
class MineFragment : BaseFragment() {


    private lateinit var homeBinding: FragmentMineBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeBinding = FragmentMineBinding.inflate(layoutInflater)
        val view = homeBinding.root
        init(view)
        return view
    }

    fun init(view: View){
        homeBinding.userName.setOnClickListener {
            findNavController().navigate(R.id.goLoginFragment)
        }
        homeBinding.goSetting.setOnClickListener {
            findNavController().navigate(R.id.goSettingFragment)
        }
        homeBinding.goFeedback.setOnClickListener {
            findNavController().navigate(R.id.goFeedbackFragment)
        }
    }
}