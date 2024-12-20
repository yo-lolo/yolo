package com.example.myapplication.ui.page.home

import android.content.Intent
import android.graphics.Outline
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ctq.sphone.market.base.BaseFragment
import com.example.myapplication.DataManager
import com.example.myapplication.R
import com.example.myapplication.common.AppConfig
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.isLogin
import com.example.myapplication.util.SelectBrowserPop
import com.example.myapplication.ui.adapter.NewsListAdapter
import com.example.myapplication.util.GlideImageLoader
import com.example.myapplication.util.createPop
import com.example.myapplication.util.getBrowserList
import com.example.myapplication.util.toBrowser
import com.example.myapplication.util.visibleOrGone
import com.example.myapplication.vm.HomeViewModel

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
    private var newsListAdapter = NewsListAdapter()
    val viewModel by viewModels<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        val view = binding.root
        init(view)
        return view
    }

    private fun init(view: View) {

        binding.toTextPage.setOnClickListener {
            findNavController().navigate(R.id.goTestFragment)
        }

        binding.homeWelcomeLayout.visibleOrGone(isLogin())
        if (isLogin()) {
            binding.textPhone.text = AppConfig.phoneNumber.toString()
        }

        binding.banner.apply {
            setDelayTime(2000) //设置轮播间隔时间
            isAutoPlay(true)  // 设置是否为自动轮播
            setImages(AppConfig.BANNER_IMAGES) //设置图片网址或地址的集合
            setImageLoader(GlideImageLoader()) //设置图片加载器
            setBannerAnimation(com.youth.banner.Transformer.Default) //设置轮播的动画效果，内含多种特效
            outlineProvider = object : ViewOutlineProvider() {
                override fun getOutline(view: View?, outline: Outline?) {
                    // 对父布局进行裁剪 设置圆角radius
                    outline!!.setRoundRect(0, 0, view!!.width, view.height, 20F)
                }
            }
            clipToOutline = true
            start() // 开始轮播
            setOnBannerListener {
                // 轮播图点击监听
                browserPop(AppConfig.BANNER_IMAGES[it])
            }
        }

        // 初始化Recycler
        binding.newsList.apply {
            layoutManager = DataManager.layoutManagerNotScroll()
            adapter = newsListAdapter
        }

        newsListAdapter.goNewsDetailListener = {
            NewsDetailFragment.goNewsDetailFragment(it, findNavController())
        }



        viewModel.newsMapData.observe(viewLifecycleOwner) { newsDataList ->
            newsListAdapter.list = newsDataList.take(2)
            newsListAdapter.notifyDataSetChanged()
        }

        binding.goNews.setOnClickListener {
            findNavController().navigate(R.id.goNewsFragment)
        }

        binding.goAddNews.setOnClickListener {
            findNavController().navigate(R.id.goAddNewsFragment)
        }

        context?.let { GlideImageLoader().displayImage(it, R.drawable.world, binding.imageTest) }

    }


    /**
     * 指定浏览器打开
     */
    private fun browserPop(jumpUrl: String) {
        val browserList = requireContext().getBrowserList()

        if (browserList.size == 1) {
            val uri = Uri.parse(jumpUrl)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            requireContext().startActivity(intent)
            return
        }

        SelectBrowserPop(requireContext(), browserList).apply {
            this.onSelectBrowser = {
                if (jumpUrl != null) {
                    requireContext().toBrowser(it, jumpUrl)
                }
            }
        }.createPop {
            this.enableDrag(true)
        }.show()
    }

    override fun onResume() {
        super.onResume()
        viewModel.initData()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}