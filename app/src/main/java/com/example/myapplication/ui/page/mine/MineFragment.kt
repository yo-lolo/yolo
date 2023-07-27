package com.example.myapplication.ui.page.mine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.blankj.utilcode.util.ToastUtils
import com.ctq.sphone.market.base.BaseFragment
import com.example.myapplication.DataManager
import com.example.myapplication.R
import com.example.myapplication.database.entity.User
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.databinding.FragmentMineBinding
import com.example.myapplication.useCase.PromptUseCase
import com.example.myapplication.vm.MineViewModel

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

    val viewModel by viewModels<MineViewModel>()
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

        homeBinding.apply {
            userName.setOnClickListener {
                findNavController().navigate(R.id.goLoginFragment)
            }
            mineEdit.setOnClickListener {
                viewModel.promptEdit()
            }
            goCollect.setOnClickListener {
                ToastUtils.showShort("还没做呢，别点我啦")
            }
            goHistory.setOnClickListener {
                ToastUtils.showShort("还没做呢，别点我啦")
            }
            goComment.setOnClickListener {
                ToastUtils.showShort("还没做呢，别点我啦")
            }
            goSetting.setOnClickListener {
                findNavController().navigate(R.id.goSettingFragment)
            }
            goFeedback.setOnClickListener {
                findNavController().navigate(R.id.goFeedbackFragment)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.initData()
    }
}