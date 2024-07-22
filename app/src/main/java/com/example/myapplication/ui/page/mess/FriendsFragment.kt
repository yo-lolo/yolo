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
import com.example.myapplication.common.AppConfig
import com.example.myapplication.databinding.FragmentFriendBinding
import com.example.myapplication.ui.adapter.EmptyViewAdapter
import com.example.myapplication.ui.adapter.FriendListAdapter
import com.example.myapplication.ui.page.mine.UserDetailFragment
import com.example.myapplication.vm.MessageViewModel

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.ui.page.mess
 * @ClassName : NewFriendsFragment
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/7/31 11:26
 * @UpdateUser : yulu
 * @UpdateDate : 2023/7/31 11:26
 * @UpdateRemark : 更新说明
 */
class FriendsFragment : BaseFragment() {

    private lateinit var binding: FragmentFriendBinding
    private val friendListAdapter = FriendListAdapter()
    val viewModel by viewModels<MessageViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFriendBinding.inflate(layoutInflater)
        initView()
        return binding.root
    }

    private fun initView() {
        binding.include.headLayout.apply {
            setTitle("我的好友")
            setBackListener {
                findNavController().popBackStack()
            }
        }

        binding.friendList.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = EmptyViewAdapter(friendListAdapter)
        }

        viewModel.mineFriendsMap.observe(viewLifecycleOwner) { friends ->
            friendListAdapter.list = friends.filter { it.key.tag == AppConfig.IS_FRIEND }
            friendListAdapter.notifyDataSetChanged()
        }

        friendListAdapter.goUserDetail = {
            UserDetailFragment.goUserDetailFragment(it, findNavController())
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.initFriendsData()
    }
}