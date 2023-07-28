package com.example.myapplication.ui.page.mess

import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.view.WindowManager
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.ctq.sphone.market.base.BaseFragment
import com.example.myapplication.DataManager
import com.example.myapplication.R
import com.example.myapplication.database.entity.FriendInfo
import com.example.myapplication.databinding.FragmentChatBinding
import com.example.myapplication.ui.adapter.ChatListAdapter
import com.example.myapplication.util.SoftInputUtil
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
class ChatFragment : BaseFragment() {

    private lateinit var binding: FragmentChatBinding
    private val chatListAdapter = ChatListAdapter()
    val viewModel by viewModels<MessageViewModel>()
    var friend: FriendInfo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        friend = arguments?.getSerializable("friend") as FriendInfo
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
        viewModel.initChats(friend!!.friendNumber)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatBinding.inflate(layoutInflater)
        initView()
        return binding.root
    }


    fun initView() {
        binding.include.headLayout.apply {
            setTitle(friend?.friendNumber.toString())
            setBackListener { findNavController().popBackStack() }
            setMenuListener {
                ChatDetailFragment.goChatDetailFragment(
                    friend!!,
                    findNavController()
                )
            }
        }
        binding.chatContent.doOnTextChanged { text, start, before, count ->
            binding.chatSubmit.visibility = if (text!!.isNotEmpty()) View.VISIBLE else View.GONE
        }

        binding.chatList.apply {
            layoutManager = DataManager.layoutManagerNotScroll()
            adapter = chatListAdapter
        }

        viewModel.chats.observe(viewLifecycleOwner) {
            chatListAdapter.list = it
            chatListAdapter.notifyDataSetChanged()
        }

        binding.chatSubmit.setOnClickListener {
            val content = binding.chatContent.text.toString().trim()
            viewModel.insertChat(friend!!.friendNumber, content)
            binding.chatContent.text.clear()
        }

    }

    /**
     * @param root             最外层布局
     * @param needToScrollView 要滚动的布局,就是说在键盘弹出的时候,你需要试图滚动上去的View,在键盘隐藏的时候,他又会滚动到原来的位置的布局
     */
    private fun controlKeyboardLayout(root: View, needToScrollView: View) {

    }

    companion object {
        fun goChatFragment(navController: NavController, friend: FriendInfo) {
            val args = Bundle().apply {
                putSerializable("friend", friend)
            }
            navController.navigate(R.id.goChatFragment, args)
        }
    }
}