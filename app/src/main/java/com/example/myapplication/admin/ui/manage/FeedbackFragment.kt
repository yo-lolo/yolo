package com.example.myapplication.admin.ui.manage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.ToastUtils
import com.ctq.sphone.market.base.BaseFragment
import com.example.myapplication.admin.adpter.FeedbacksAdapter
import com.example.myapplication.admin.adpter.ImagesAdapter
import com.example.myapplication.admin.vm.ManageViewModel
import com.example.myapplication.databinding.FragmentTwoBinding
import com.example.myapplication.ui.adapter.EmptyViewAdapter
import com.example.myapplication.useCase.PromptUseCase


class FeedbackFragment : BaseFragment() {

    private lateinit var binding: FragmentTwoBinding
    private val feedbacksAdapter = FeedbacksAdapter()
    private val viewModel by viewModels<ManageViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.initFeedbacks()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTwoBinding.inflate(layoutInflater)
        initView()
        return binding.root
    }

    private fun initView() {
        binding.manageFeedbacks.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = EmptyViewAdapter(feedbacksAdapter)
        }

        viewModel.feedbacks.observe(viewLifecycleOwner) {
            feedbacksAdapter.list = it
            feedbacksAdapter.notifyDataSetChanged()
        }

        feedbacksAdapter.showBigImageListener = {
            PromptUseCase().promptBigImage(it)
        }
    }

}