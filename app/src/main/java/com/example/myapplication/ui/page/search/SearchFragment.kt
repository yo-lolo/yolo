package com.example.myapplication.ui.page.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.KeyboardUtils
import com.ctq.sphone.market.base.BaseFragment
import com.example.myapplication.R
import com.example.myapplication.config.AppConfig
import com.example.myapplication.databinding.FragmentAppSearchBinding
import com.example.myapplication.databinding.LayoutSearchHeadBinding
import com.example.myapplication.ui.adapter.EmptyViewAdapter
import com.example.myapplication.ui.adapter.FriendListAdapter
import com.example.myapplication.ui.adapter.NewsListAdapter
import com.example.myapplication.ui.page.home.NewsDetailFragment
import com.example.myapplication.ui.page.mine.UserDetailFragment
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
    private var tag: Int? = null
    val viewModel by viewModels<AppSearchViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentAppSearchBinding.inflate(inflater)
        searchHeadBinding = LayoutSearchHeadBinding.inflate(inflater)
        tag = arguments?.getInt("tag")
        tag?.let { initAdapter(it) }
        initView()
        return viewBinding.root
    }

    private fun initView() {
        viewBinding.include.headLayout.bindLeftView(searchHeadBinding.root)
        searchHeadBinding.includeBack.backImage.setOnClickListener {
            findNavController().popBackStack()
        }
        searchHeadBinding.searchView.searchButtonListener = {
            viewModel.doSearch(tag, it)
            KeyboardUtils.hideSoftInput(searchHeadBinding.searchView)
        }

        searchHeadBinding.searchView.searchEditText.setOnEditorActionListener { v, keyCode, event ->
            if (keyCode == EditorInfo.IME_ACTION_SEARCH) {
                viewModel.doSearch(tag, searchHeadBinding.searchView.searchEditText.text.toString())
                KeyboardUtils.hideSoftInput(searchHeadBinding.searchView)
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }

        initProgress(viewModel.loadingTaskCount)
    }

    private fun initAdapter(tag: Int) {
        if (tag == AppConfig.SEARCH_NEWS) {
            val adapter = NewsListAdapter()
            initNewsAdapter(adapter)
        } else if (tag == AppConfig.SEARCH_FRIENDS) {
            val adapter = FriendListAdapter()
            initFriendsAdapter(adapter)
        }
    }

    private fun initNewsAdapter(newsAdapter: NewsListAdapter) {
        viewModel.searchNewsResult.observe(viewLifecycleOwner) {
            newsAdapter.list = it
            newsAdapter.notifyDataSetChanged()
        }

        newsAdapter.goNewsDetailListener = {
            NewsDetailFragment.goNewsDetailFragment(it, findNavController())
        }

        viewBinding.appsRecyclew.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = EmptyViewAdapter(newsAdapter)
        }
    }

    private fun initFriendsAdapter(friendsAdapter: FriendListAdapter) {
        viewModel.searchFriendsResult.observe(viewLifecycleOwner) {
            friendsAdapter.list = it
            friendsAdapter.notifyDataSetChanged()
        }

        friendsAdapter.goUserDetail = {
            UserDetailFragment.goUserDetailFragment(it, findNavController())
        }

        viewBinding.appsRecyclew.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = EmptyViewAdapter(friendsAdapter)
        }
    }

    companion object {
        fun goSearchFragment(tag: Int, navController: NavController) {
            val bundle = Bundle().apply { putInt("tag", tag) }
            navController.navigate(R.id.goSearchFragment, bundle)
        }
    }

}