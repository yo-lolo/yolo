package com.example.myapplication.ui.page.mess

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ctq.sphone.market.base.BaseFragment
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentMessBinding
import com.example.myapplication.ui.adapter.EmptyViewAdapter
import com.example.myapplication.ui.adapter.FriendListAdapter
import com.example.myapplication.ui.adapter.MessListAdapter
import com.example.myapplication.vm.MessageViewModel

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.ui.page.mess
 * @ClassName : MessageFragment
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/7/25 17:36
 * @UpdateUser : yulu
 * @UpdateDate : 2023/7/25 17:36
 * @UpdateRemark : 更新说明
 */
class MessageFragment : BaseFragment() {

    private lateinit var binding: FragmentMessBinding
    private val messListAdapter = MessListAdapter()
    val viewModel by viewModels<MessageViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMessBinding.inflate(layoutInflater)
        initView()
        viewModel.initData()
        return binding.root
    }


    fun initView() {
        binding.include.headLayout.apply {
            setTitle("信息")
        }
        val linearLayoutManager = object : LinearLayoutManager(context) {
            override fun canScrollVertically(): Boolean {
                return false
            }
        }
        binding.messList.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = EmptyViewAdapter(messListAdapter)
        }


        messListAdapter.goChatListener = {
            ChatFragment.goChatFragment(findNavController(), it)
        }

        binding.goNewFriends.setOnClickListener {
            findNavController().navigate(R.id.goNewFriendsFragment)
        }

        binding.goFriends.setOnClickListener {
            findNavController().navigate(R.id.goFriendsFragment)
        }
    }
}