package com.example.myapplication.ui.page.mine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ctq.sphone.market.base.BaseFragment
import com.example.myapplication.databinding.FragmentNewFriendsBinding
import com.example.myapplication.databinding.FragmentUserDetailBinding

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.ui.page.mine
 * @ClassName : UserDetailFragmet
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/7/31 14:04
 * @UpdateUser : yulu
 * @UpdateDate : 2023/7/31 14:04
 * @UpdateRemark : 更新说明
 */
class UserDetailFragment : BaseFragment() {

    private lateinit var binding: FragmentUserDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    companion object {
        fun goUserDetailFragment(){

        }
    }
}