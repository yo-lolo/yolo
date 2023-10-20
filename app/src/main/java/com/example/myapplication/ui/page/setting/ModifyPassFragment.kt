package com.example.myapplication.ui.page.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ctq.sphone.market.base.BaseFragment
import com.example.myapplication.databinding.FragmentModifyPassBinding

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
    }

}