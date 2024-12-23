package com.example.myapplication.shareLifeUser.ui.mess

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.KeyboardUtils
import com.example.myapplication.base.baseUi.BaseFragment
import com.example.myapplication.R
import com.example.myapplication.common.Constants
import com.example.myapplication.databinding.FragmentChatBinding
import com.example.myapplication.shareLifeUser.adapter.ChatListAdapter
import com.example.myapplication.shareLifeUser.ui.mine.UserDetailFragment
import com.example.myapplication.shareLifeUser.vm.MessageViewModel


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
    var receiver: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        receiver = arguments?.getLong(Constants.RECEIVER)
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
        viewModel.initChats(receiver!!)
        //fix:解决软键盘遮挡布局的问题
        KeyboardUtils.fixAndroidBug5497(requireActivity())
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
        viewModel.friendUserInfo.observe(viewLifecycleOwner) { user ->
            binding.include.headLayout.apply {
                setTitle(user.neck)
                setBackListener { findNavController().popBackStack() }
                setMenuListener {
                    ChatDetailFragment.goChatDetailFragment(
                        receiver!!,
                        findNavController()
                    )
                }
            }
        }

        binding.chatContent.doOnTextChanged { text, start, before, count ->
            binding.chatSubmit.visibility = if (text!!.isNotEmpty()) View.VISIBLE else View.GONE
        }

        binding.chatList.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = chatListAdapter
        }

        viewModel.chatsMap.observe(viewLifecycleOwner) {
            chatListAdapter.list = it
            chatListAdapter.notifyDataSetChanged()
        }

        chatListAdapter.goUserDetailListener = {
            UserDetailFragment.goUserDetailFragment(it, findNavController())
        }

        binding.chatSubmit.setOnClickListener {
            val content = binding.chatContent.text.toString().trim()
            viewModel.insertChat(receiver!!, content)
            binding.chatContent.text.clear()
        }

        binding.chatRefresh.setFooterHeight(0f)

    }

    companion object {

        fun goChatFragment(navController: NavController, friendNumber: Long) {
            val args = Bundle().apply {
                putLong(Constants.RECEIVER, friendNumber)
            }
            navController.navigate(R.id.goChatFragment, args)
        }
    }
}