package com.example.myapplication.ui.page.home

import android.content.Context
import android.graphics.Outline
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.ctq.sphone.market.base.BaseFragment
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.ui.page.detail.DetailOneFragment
import com.example.myapplication.ui.page.detail.DetailTwoFragment
import com.example.myapplication.view.HeadLayout
import com.example.myapplication.view.SearchStubView
import com.youth.banner.loader.ImageLoader

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

        val images = listOf(
            "https://c-ssl.duitang.com/uploads/blog/202102/06/20210206215858_cf107.jpg",
            "https://c-ssl.dtstatic.com/uploads/item/201902/10/20190210213858_uwecj.thumb.1000_0.jpg",
            "https://c-ssl.dtstatic.com/uploads/item/201702/21/20170221152232_45imE.thumb.1000_0.jpeg",
            "https://c-ssl.dtstatic.com/uploads/item/201707/07/20170707101903_23Bax.thumb.1000_0.jpeg",
            "https://c-ssl.dtstatic.com/uploads/item/202004/16/20200416114418_bunuf.thumb.1000_0.jpg"
        )

        binding.banner.apply {
            setDelayTime(2000)
            isAutoPlay(true)  //
            setImages(images)
            setImageLoader(GlideImageLoader())
            outlineProvider = object : ViewOutlineProvider(){
                override fun getOutline(view: View?, outline: Outline?) {
                    outline!!.setRoundRect(0,0,view!!.width,view!!.height, 20F)
                }
            }
            clipToOutline = true
            start()
        }

        binding.banner.setOnBannerListener {
            Toast.makeText(activity, "你点击了TWICE的第${it}张轮播图", Toast.LENGTH_SHORT).show()
        }

    }


    class GlideImageLoader : ImageLoader() {
        override fun displayImage(context: Context?, path: Any?, imageView: ImageView?) {
            Glide.with(context).load(path).error(R.mipmap.icon_empty).into(imageView)
        }
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