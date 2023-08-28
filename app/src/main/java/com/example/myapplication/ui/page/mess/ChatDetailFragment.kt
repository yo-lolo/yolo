package com.example.myapplication.ui.page.mess

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.ctq.sphone.market.base.BaseFragment
import com.example.myapplication.R
import com.example.myapplication.database.entity.ChatInfo
import com.example.myapplication.database.entity.FriendInfo
import com.example.myapplication.databinding.FragmentChatDetailBinding
import com.example.myapplication.ui.page.mine.UserDetailFragment
import com.example.myapplication.util.GlideImageLoader
import com.example.myapplication.util.JsonUtil
import com.example.myapplication.vm.MessageViewModel

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.ui.page.mess
 * @ClassName : ChatDetailFragment
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/7/27 16:41
 * @UpdateUser : yulu
 * @UpdateDate : 2023/7/27 16:41
 * @UpdateRemark : 更新说明
 */
class ChatDetailFragment : BaseFragment() {

    private lateinit var binding: FragmentChatDetailBinding
    var number: Long? = null
    val viewModel by viewModels<MessageViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        number = arguments?.getLong("friendNumber")
        viewModel.initChats(number!!)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatDetailBinding.inflate(layoutInflater)
        initView()
        return binding.root
    }

    fun initView() {
        binding.include.headLayout.apply {
            setTitle("聊天详情")
            setBackListener { findNavController().popBackStack() }
        }
        binding.userIcon.setOnClickListener {
            UserDetailFragment.goUserDetailFragment(number!!, findNavController())
        }
        viewModel.friendUserInfo.observe(viewLifecycleOwner) {
            GlideImageLoader().displayLocalFile(it.image, binding.userIcon)
            binding.userNeck.text = it.neck
        }

    }

    companion object {
        fun goChatDetailFragment(
            friendNumber: Long,
            navController: NavController
        ) {
            val args = Bundle().apply {
                putLong("friendNumber", friendNumber)
            }
            navController.navigate(R.id.goChatDetailFragment, args)
        }
    }
}