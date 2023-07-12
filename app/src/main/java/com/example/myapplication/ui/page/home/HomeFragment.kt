package com.example.myapplication.ui.page.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.PagerAdapter
import com.ctq.sphone.market.base.BaseFragment
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.ui.page.detail.DetailOneFragment
import com.example.myapplication.ui.page.detail.DetailTwoFragment
import com.example.myapplication.view.HeadLayout
import com.example.myapplication.view.SearchStubView

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.ui.page.home
 * @ClassName : HomeFragment
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/6/29 13:44
 * @UpdateUser : yulu
 * @UpdateDate : 2023/6/29 13:44
 * @UpdateRemark : 更新说明
 */
class HomeFragment : BaseFragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        val view = binding.root
        init(view)
        return view
    }

    private fun init(view: View) {
        val context = view.context
        val navController = findNavController()
        initSearchBar(context, binding.include.headLayout, navController)
    }

    private fun initSearchBar(
        context: Context,
        headLayout: HeadLayout,
        navController: NavController
    ) {
        val searchStubView = SearchStubView(context)
        headLayout.bindLeftView(searchStubView)
        searchStubView.setOnClickListener {
            navController.navigate(R.id.goSearchFragment)
        }
    }
}