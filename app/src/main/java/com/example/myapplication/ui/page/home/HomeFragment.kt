package com.example.myapplication.ui.page.home

import android.graphics.Outline
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ToastUtils
import com.ctq.sphone.market.base.BaseFragment
import com.example.myapplication.R
import com.example.myapplication.config.AppConfig
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.ui.adapter.NewsListAdapter
import com.example.myapplication.util.GlideImageLoader
import com.example.myapplication.util.TimeUtil
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
        viewModel.initData()
        return view
    }

    private fun init(view: View) {

        binding.textPhone.text = AppConfig.phoneNumber.toString()

        // 轮播图片集合
        val images = listOf(
            "https://alifei02.cfp.cn/creative/vcg/800/new/VCG21gic19937006.jpg",
            "https://alifei01.cfp.cn/creative/vcg/800/version23/VCG41157532246.jpg",
            "https://tenfei04.cfp.cn/creative/vcg/800/version23/VCG41164857181.jpg",
            "https://alifei05.cfp.cn/creative/vcg/800/version23/VCG41542511145.jpg",
            "https://tenfei03.cfp.cn/creative/vcg/800/new/VCG41N1090223986.jpg"
        )

        binding.banner.apply {
            setDelayTime(2000) //设置轮播间隔时间
            isAutoPlay(true)  // 设置是否为自动轮播
            setImages(images) //设置图片网址或地址的集合
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
        }

        // 轮播图的监听方法
        binding.banner.setOnBannerListener {
            ToastUtils.showShort("你点击了第${it + 1}张轮播图")
        }

        // 设置RecyclerView在垂直状态下不滑动
        var linearlayoutManager: LinearLayoutManager = object : LinearLayoutManager(context) {
            override fun canScrollVertically(): Boolean {
                return false
            }
        }
        // 初始化Recycler
        binding.newsList.apply {
            layoutManager = linearlayoutManager
            adapter = newsListAdapter
        }

        newsListAdapter.goNewsDetailListener = {
            NewsDetailFragment.goNewsDetailFragment(it, findNavController())
        }

        viewModel.news.observe(viewLifecycleOwner) { news ->
            newsListAdapter.list = news.filter { !TimeUtil.isToday(it.time) }.take(2)
            newsListAdapter.notifyDataSetChanged()
        }

        binding.goNews.setOnClickListener {
            findNavController().navigate(R.id.goNewsFragment)
        }

        binding.goAddNews.setOnClickListener {
            findNavController().navigate(R.id.goAddNewsFragment)
        }

        GlideImageLoader().displayImage(context, R.drawable.world, binding.imageTest)

    }

}