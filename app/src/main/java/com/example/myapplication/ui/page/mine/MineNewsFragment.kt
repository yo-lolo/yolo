package com.example.myapplication.ui.page.mine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ctq.sphone.market.base.BaseFragment
import com.example.myapplication.R
import com.example.myapplication.config.AppConfig
import com.example.myapplication.databinding.FragmentMineNewsBinding
import com.example.myapplication.ui.adapter.NewsListAdapter
import com.example.myapplication.ui.page.home.NewsDetailFragment
import com.example.myapplication.vm.MineViewModel

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.ui.page.mine
 * @ClassName : MineNewsFragment
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/8/23 17:57
 * @UpdateUser : yulu
 * @UpdateDate : 2023/8/23 17:57
 * @UpdateRemark : 更新说明
 */
class MineNewsFragment : BaseFragment() {


    private lateinit var binding: FragmentMineNewsBinding
    var number: Long? = null
    var tag: Int? = null
    val viewModel by viewModels<MineViewModel>()
    private val newsMineAdapter = NewsListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        arguments?.apply {
            number = getLong("number")
            tag = getInt("tag")
        }
        viewModel.initData(number!!)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMineNewsBinding.inflate(layoutInflater)
        val view = binding.root
        initView(view)
        return view
    }

    private fun initView(view: View) {
        binding.include.headLayout.apply {
            setTitle("我的文章")
            setBackListener { findNavController().popBackStack() }
        }

        viewModel.news.observe(viewLifecycleOwner) {
            newsMineAdapter.list = it
            newsMineAdapter.notifyDataSetChanged()
        }

        binding.mineNews.apply {
            adapter = newsMineAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }

        newsMineAdapter.goNewsDetailListener = {
            NewsDetailFragment.goNewsDetailFragment(it, findNavController())
        }

    }


    companion object {
        /**
         * Tag 0:是从我的页面跳转来 使用默认布局
         * Tag 1:从用户详情跳转来 修改布局 需要传入用户Id
         */
        fun goMineNewsFragment(
            navController: NavController,
            number: Long = AppConfig.phoneNumber,
            tag: Int = 0
        ) {
            val args = Bundle().apply {
                putLong("number", number)
                putInt("tag", tag)
            }
            navController.navigate(R.id.goMineNewsFragment, args)
        }
    }
}