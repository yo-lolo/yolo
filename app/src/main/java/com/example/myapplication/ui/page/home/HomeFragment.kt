package com.example.myapplication.ui.page.home

import android.graphics.Outline
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.ToastUtils
import com.ctq.sphone.market.base.BaseFragment
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.ui.adapter.TwiceListAdapter
import com.example.myapplication.util.GlideImageLoader

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
    private var twiceListAdapter = TwiceListAdapter()

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

        binding.textSearch.setOnClickListener {
            findNavController().navigate(R.id.goSearchFragment)
        }

        // 轮播图片集合
        val images = listOf(
            "https://c-ssl.duitang.com/uploads/blog/202102/06/20210206215858_cf107.jpg",
            "https://c-ssl.dtstatic.com/uploads/item/201902/10/20190210213858_uwecj.thumb.1000_0.jpg",
            "https://c-ssl.dtstatic.com/uploads/item/201702/21/20170221152232_45imE.thumb.1000_0.jpeg",
            "https://c-ssl.dtstatic.com/uploads/item/201707/07/20170707101903_23Bax.thumb.1000_0.jpeg",
            "https://c-ssl.dtstatic.com/uploads/item/202004/16/20200416114418_bunuf.thumb.1000_0.jpg"
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
            ToastUtils.showShort("你点击了TWICE的第${it + 1}张轮播图")
        }

        // 初始化Recycler
        binding.twiceList.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = twiceListAdapter
        }

        GlideImageLoader().displayImage(context, R.drawable.world, binding.imageTest)

    }

}