package com.example.myapplication.admin.ui.manage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ctq.sphone.market.base.BaseFragment
import com.example.myapplication.admin.adpter.UserListAdapter
import com.example.myapplication.admin.vm.UserViewModel
import com.example.myapplication.databinding.FragmentOneBinding
import com.example.myapplication.ui.adapter.EmptyViewAdapter


class OneFragment : BaseFragment() {

    private lateinit var binding: FragmentOneBinding
    private val userListAdapter = UserListAdapter()
    private val viewModel by viewModels<UserViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOneBinding.inflate(layoutInflater)
        val view = binding.root
        initView(view)
        return view
    }

    private fun initView(view: View) {

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
        viewModel.initData()
    }

}