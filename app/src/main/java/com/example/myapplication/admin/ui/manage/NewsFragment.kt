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
import com.example.myapplication.admin.adpter.NewsListAdapter
import com.example.myapplication.admin.vm.ManageViewModel
import com.example.myapplication.databinding.FragmentThreeBinding
import com.example.myapplication.useCase.PromptUseCase


class NewsFragment : BaseFragment() {

    private lateinit var binding: FragmentThreeBinding
    private val newsListAdapter = NewsListAdapter()
    private val viewModel by viewModels<ManageViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentThreeBinding.inflate(layoutInflater)
        val view = binding.root
        initView(view)
        viewModel.initNews()
        return view
    }

    private fun initView(view: LinearLayout) {

        binding.include.headLayout.apply {
            setTitle("新闻")
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

        newsListAdapter.showContentListener = {
            NewsDetailFragment.goNewsDetailFragment(it, findNavController())
        }

    }

}