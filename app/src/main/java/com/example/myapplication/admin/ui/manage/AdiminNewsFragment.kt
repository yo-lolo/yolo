package com.example.myapplication.admin.ui.manage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ctq.sphone.market.base.BaseFragment
import com.example.myapplication.admin.adpter.AdminNewsListAdapter
import com.example.myapplication.admin.vm.ManageViewModel
import com.example.myapplication.databinding.FragmentManageNewsBinding


class AdiminNewsFragment : BaseFragment() {

    private lateinit var binding: FragmentManageNewsBinding
    private val newsListAdapter = AdminNewsListAdapter()
    private val viewModel by viewModels<ManageViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentManageNewsBinding.inflate(layoutInflater)
        val view = binding.root
        initView(view)
        viewModel.initNews()
        return view
    }

    private fun initView(view: LinearLayout) {

        binding.include.headLayout.apply {
            setTitle("文章")
            setHeadLayoutColor()
            setBackListener { findNavController().popBackStack() }
        }

        binding.newsList.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = newsListAdapter
        }

        viewModel.newsList.observe(viewLifecycleOwner) {
            newsListAdapter.list = it
            newsListAdapter.notifyDataSetChanged()
        }


        newsListAdapter.apply {
            showContentListener = {
                AdminNewsDetailFragment.goAdminNewsDetailFragment(it, findNavController())
            }
            auditPassListener = { newInfo ->
                viewModel.updateNewsAuditType(1, newInfo)
            }
            auditFailListener = { newInfo ->
                viewModel.updateNewsAuditType(-1, newInfo)
            }
            auditUndoListener = { newInfo ->
                viewModel.updateNewsAuditType(0, newInfo)
            }
        }

    }

}