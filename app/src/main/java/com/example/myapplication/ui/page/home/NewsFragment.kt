package com.example.myapplication.ui.page.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ctq.sphone.market.base.BaseFragment
import com.example.myapplication.config.AppConfig
import com.example.myapplication.databinding.FragmentNewsBinding
import com.example.myapplication.ui.adapter.EmptyViewAdapter
import com.example.myapplication.ui.adapter.NewsListAdapter
import com.example.myapplication.ui.page.search.SearchFragment
import com.example.myapplication.vm.HomeViewModel

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.ui.page.home
 * @ClassName : NewsFragment
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/7/26 10:44
 * @UpdateUser : yulu
 * @UpdateDate : 2023/7/26 10:44
 * @UpdateRemark : 更新说明
 */
class NewsFragment : BaseFragment() {

    private var newsListAdapter = NewsListAdapter()
    private lateinit var binding: FragmentNewsBinding
    val viewModel by viewModels<HomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsBinding.inflate(layoutInflater)
        initView()
        return binding.root
    }

    fun initView() {

        binding.include.headLayout.apply {
            setTitle("兔国要闻")
            setBackListener { findNavController().popBackStack() }
        }

        binding.textSearch.setOnClickListener {
            SearchFragment.goSearchFragment(
                AppConfig.SEARCH_NEWS,
                findNavController()
            )
        }

        binding.newsList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = EmptyViewAdapter(newsListAdapter)
        }

        viewModel.newsMapData.observe(viewLifecycleOwner) {
            newsListAdapter.list = it
            newsListAdapter.notifyDataSetChanged()
        }

        newsListAdapter.goNewsDetailListener = {
            NewsDetailFragment.goNewsDetailFragment(it, findNavController())
        }

        binding.refreshLayout.apply {
            setEnableRefresh(true)
            setOnRefreshListener{
                viewModel.initData()
            }
            setEnableLoadMore(false)
        }

        viewModel.loadingTaskCount.observe(viewLifecycleOwner) { loading ->
            if (loading > 0) {
                binding.refreshLayout.finishRefresh()
            }
        }

    }

    override fun onResume() {
        viewModel.initData()
        super.onResume()
    }
}