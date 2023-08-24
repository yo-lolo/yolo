package com.example.myapplication.ui.page.mess

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ctq.sphone.market.base.BaseFragment
import com.example.myapplication.DataManager
import com.example.myapplication.databinding.FragmentNewFriendsBinding
import com.example.myapplication.ui.adapter.EmptyViewAdapter
import com.example.myapplication.ui.adapter.NewFriendListAdapter
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
class NewFriendsFragment : BaseFragment() {

    private lateinit var binding: FragmentNewFriendsBinding
    var newFriendsAdapter = NewFriendListAdapter()
    val viewModel by viewModels<MessageViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewFriendsBinding.inflate(layoutInflater)
        initView()
        return binding.root
    }

    private fun initView() {
        binding.include.headLayout.apply {
            setTitle("新的朋友")
            setBackListener {
                findNavController().popBackStack()
            }
        }

        binding.newFriendsList.apply {
            layoutManager = DataManager.layoutManagerNotScroll()
            adapter = EmptyViewAdapter(newFriendsAdapter)
        }

        viewModel.newFriends.observe(viewLifecycleOwner) {
            newFriendsAdapter.list = it
            newFriendsAdapter.notifyDataSetChanged()
        }

        newFriendsAdapter.goUserDetail = {number ->
            UserDetailFragment.goUserDetailFragment(number, findNavController())
        }

        newFriendsAdapter.agreeFriendListener = { id, number ->
            viewModel.agreeFriend(id, number)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.initData()
    }
}