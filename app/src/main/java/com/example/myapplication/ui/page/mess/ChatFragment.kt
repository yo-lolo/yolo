package com.example.myapplication.ui.page.mess

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.ToastUtils
import com.ctq.sphone.market.base.BaseFragment
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentChatBinding
import com.example.myapplication.ui.adapter.EmptyViewAdapter
import com.example.myapplication.ui.adapter.FriendListAdapter

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
    private val friendListAdapter = FriendListAdapter()
    var name: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        name = arguments?.getString("data")
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
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
            setTitle(name!!)
            setBackListener { findNavController().popBackStack() }
            setMenuListener { ToastUtils.showShort("这里是菜单") }
        }

    }

    override fun onResume() {
        super.onResume()

    }

    companion object {
        fun goChatFragment(navController: NavController, data: String) {
            val args = Bundle().apply {
                putString("data", data)
            }
            navController.navigate(R.id.goChatFragment, args)
        }
    }
}