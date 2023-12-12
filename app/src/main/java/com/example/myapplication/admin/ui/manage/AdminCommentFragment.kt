package com.example.myapplication.admin.ui.manage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ctq.sphone.market.base.BaseFragment
import com.example.myapplication.admin.adpter.AdminCommentsAdapter
import com.example.myapplication.admin.vm.ManageViewModel
import com.example.myapplication.databinding.FragmentManageCommentsBinding
import com.example.myapplication.ui.adapter.EmptyViewAdapter


class AdminCommentFragment : BaseFragment() {

    private lateinit var binding: FragmentManageCommentsBinding
    private val commentsAdapter = AdminCommentsAdapter()
    private val viewModel by viewModels<ManageViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentManageCommentsBinding.inflate(layoutInflater)
        initView()
        return binding.root
    }

    private fun initView() {

        binding.include.headLayout.apply {
            setTitle("评论")
            setHeadLayoutColor()
            setBackListener { findNavController().popBackStack() }
        }

        binding.manageComments.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = EmptyViewAdapter(commentsAdapter)
        }

        binding.commentsBatchAudit.setOnClickListener {
            viewModel.onBatchAuditComments()
        }

        binding.commentsSelectAll.setOnClickListener {
            viewModel.onCheckAll()
        }

        commentsAdapter.checkedChangeListener = {
            viewModel.onCheckChange()
        }

        viewModel.isAllCheck.observe(viewLifecycleOwner) { isAllCheck ->
            binding.commentsSelectAll.text = if (isAllCheck) "全不选" else "全选"
        }

        viewModel.comments.observe(viewLifecycleOwner) {
            commentsAdapter.list = it
            commentsAdapter.notifyDataSetChanged()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.initComments()
    }

}