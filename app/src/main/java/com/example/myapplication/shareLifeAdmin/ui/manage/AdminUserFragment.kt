package com.example.myapplication.shareLifeAdmin.ui.manage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.base.baseUi.BaseFragment
import com.example.myapplication.shareLifeAdmin.adpter.AdminUserListAdapter
import com.example.myapplication.shareLifeAdmin.vm.ManageViewModel
import com.example.myapplication.databinding.FragmentManageUserBinding
import com.example.myapplication.shareLifeUser.adapter.EmptyViewAdapter


class AdminUserFragment : BaseFragment() {

    private lateinit var binding: FragmentManageUserBinding
    private val userListAdapter = AdminUserListAdapter()
    private val viewModel by viewModels<ManageViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentManageUserBinding.inflate(layoutInflater)
        val view = binding.root
        initView(view)
        return view
    }

    private fun initView(view: View) {

        binding.include.headLayout.apply {
            setTitle("用户")
            setHeadLayoutColor()
            setBackListener { findNavController().popBackStack() }
        }

        binding.userList.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = EmptyViewAdapter(userListAdapter)
        }

        viewModel.userList.observe(viewLifecycleOwner) {
            userListAdapter.list = it
            userListAdapter.notifyDataSetChanged()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.initUsersData()
    }

}