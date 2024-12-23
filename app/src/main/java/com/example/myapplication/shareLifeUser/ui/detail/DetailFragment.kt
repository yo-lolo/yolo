package com.example.myapplication.shareLifeUser.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import com.example.myapplication.base.baseUi.BaseFragment
import com.example.myapplication.databinding.FragmentDetailBinding

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.ui.page.detail
 * @ClassName : DetailFragment
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/6/29 15:52
 * @UpdateUser : yulu
 * @UpdateDate : 2023/6/29 15:52
 * @UpdateRemark : 更新说明
 */
class DetailFragment : BaseFragment() {

    lateinit var binding: FragmentDetailBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(layoutInflater)
        val view = binding.root
        init(view)
        return view
    }

    fun init(view: View) {
        val titles = listOf("发现", "关注")
        val fragments = listOf(DetailOneFragment(), DetailTwoFragment())

        binding.viewPager.adapter = object : FragmentPagerAdapter(childFragmentManager) {
            override fun getCount(): Int {
                return titles.size
            }

            override fun getItem(position: Int): Fragment {
                return fragments[position]
            }

            override fun getPageTitle(position: Int): CharSequence? {
                return titles[position]
            }

        }

        binding.tabLayout.setupWithViewPager(binding.viewPager)
    }

}