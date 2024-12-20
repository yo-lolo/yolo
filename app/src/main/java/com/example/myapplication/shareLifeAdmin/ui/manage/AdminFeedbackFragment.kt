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
import com.example.myapplication.shareLifeAdmin.adpter.AdminFeedbacksAdapter
import com.example.myapplication.shareLifeAdmin.vm.ManageViewModel
import com.example.myapplication.databinding.FragmentManageFeedbacksBinding
import com.example.myapplication.shareLifeUser.adapter.EmptyViewAdapter
import com.example.myapplication.util.PromptUtils


class AdminFeedbackFragment : BaseFragment() {

    private lateinit var binding: FragmentManageFeedbacksBinding
    private val feedbacksAdapter = AdminFeedbacksAdapter()
    private val viewModel by viewModels<ManageViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.initFeedbacks()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentManageFeedbacksBinding.inflate(layoutInflater)
        initView()
        return binding.root
    }

    private fun initView() {

        binding.include.headLayout.apply {
            setTitle("反馈")
            setHeadLayoutColor()
            setBackListener { findNavController().popBackStack() }
        }

        binding.manageFeedbacks.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = EmptyViewAdapter(feedbacksAdapter)
        }

        viewModel.feedbacks.observe(viewLifecycleOwner) {
            feedbacksAdapter.list = it
            feedbacksAdapter.notifyDataSetChanged()
        }

        feedbacksAdapter.showBigImageListener = {
            PromptUtils().promptBigImage(it)
        }
    }

}