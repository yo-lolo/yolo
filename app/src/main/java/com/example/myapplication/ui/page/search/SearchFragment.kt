package com.example.myapplication.ui.page.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.KeyboardUtils
import com.ctq.sphone.market.base.BaseFragment
import com.example.myapplication.databinding.FragmentAppSearchBinding
import com.example.myapplication.databinding.LayoutSearchHeadBinding
import com.example.myapplication.ui.adapter.EmptyViewAdapter
import com.example.myapplication.ui.adapter.NewsListAdapter
import com.example.myapplication.ui.page.home.NewsDetailFragment
import com.example.myapplication.vm.AppSearchViewModel

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.ui.page.search
 * @ClassName : SeachFragment
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/6/29 16:02
 * @UpdateUser : yulu
 * @UpdateDate : 2023/6/29 16:02
 * @UpdateRemark : 更新说明
 */
class SearchFragment : BaseFragment() {

    private lateinit var viewBinding: FragmentAppSearchBinding
    private lateinit var searchHeadBinding: LayoutSearchHeadBinding


    val viewModel by viewModels<AppSearchViewModel>()
    private val searchListAdapter = NewsListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentAppSearchBinding.inflate(inflater)
        searchHeadBinding = LayoutSearchHeadBinding.inflate(inflater)
        initView()
        return viewBinding.root
    }

    private fun initView() {
        viewBinding.include.headLayout.bindLeftView(searchHeadBinding.root)
        searchHeadBinding.includeBack.backImage.setOnClickListener {
            findNavController().popBackStack()
        }

        searchHeadBinding.searchView.searchButtonListener = {
            viewModel.searchNews(it)
            KeyboardUtils.hideSoftInput(searchHeadBinding.searchView)
        }

        searchHeadBinding.searchView.searchEditText.setOnEditorActionListener { v, keyCode, event ->
            if (keyCode == EditorInfo.IME_ACTION_SEARCH) {
                viewModel.searchNews(searchHeadBinding.searchView.searchEditText.text.toString())
                KeyboardUtils.hideSoftInput(searchHeadBinding.searchView)
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }

        viewBinding.appsRecyclew.apply {
            layoutManager = LinearLayoutManager(context)
            adapter =  EmptyViewAdapter(searchListAdapter)
        }

        viewModel.searchResult.observe(viewLifecycleOwner) {
            searchListAdapter.list = it
            searchListAdapter.notifyDataSetChanged()
        }

        searchListAdapter.goNewsDetailListener = {
            NewsDetailFragment.goNewsDetailFragment(it, findNavController())
        }

        initProgress(viewModel.loadingTaskCount)
    }

}