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
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentAppSearchBinding
import com.example.myapplication.databinding.LayoutSearchHeadBinding
import com.example.myapplication.ui.adapter.AppSeachAdapter
import com.example.myapplication.ui.adapter.EmptyViewAdapter
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

    lateinit var viewBinding: FragmentAppSearchBinding
    lateinit var searchHeadBinding: LayoutSearchHeadBinding


    val viewModel by viewModels<AppSearchViewModel>()
    private val searchListAdapter = AppSeachAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentAppSearchBinding.inflate(inflater)
        searchHeadBinding = LayoutSearchHeadBinding.inflate(inflater)
        initView()
        initData()
        return viewBinding.root
    }

    private fun initView() {
        viewBinding.include.headLayout.bindLeftView(searchHeadBinding.root)
        searchHeadBinding.includeBack.backImage.setOnClickListener {
            findNavController().popBackStack()
        }

        searchHeadBinding.searchView.searchButtonListener = {
            viewModel.searchApp(it)
            KeyboardUtils.hideSoftInput(searchHeadBinding.searchView)
        }

        searchHeadBinding.searchView.searchEditText.setOnEditorActionListener { v, keyCode, event ->
            if (keyCode == EditorInfo.IME_ACTION_SEARCH) {
                viewModel.searchApp(searchHeadBinding.searchView.searchEditText.text.toString())
                KeyboardUtils.hideSoftInput(searchHeadBinding.searchView)
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }

        viewBinding.appsRecyclew.apply {
            layoutManager = LinearLayoutManager(context)
            //adapter
        }

        viewBinding.recommentApps.apply {
            setTitle("推荐应用")
            setSubTitle("更多") {
//                findNavController().navigate(R.id.goRecommentAppFragment)
            }
        }

        /*searchHeadBinding.searchView.searchEditText.addTextChangedListener(afterTextChanged = { text ->
            viewBinding.recommentApps.visibility =
                if (text.isNullOrBlank()) View.VISIBLE else View.GONE
        })*/

        searchListAdapter.fragmentLifecycle = viewLifecycleOwner.lifecycle

        viewModel.searchResult.observe(viewLifecycleOwner) {
            searchListAdapter.list = it
            searchListAdapter.notifyDataSetChanged()
        }


        initProgress(viewModel.loadingTaskCount)
    }

    private fun initData() {

    }

}