package com.example.myapplication.manager.ui.manage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import com.ctq.sphone.market.base.BaseFragment
import com.example.myapplication.databinding.FragmentManagerTestBinding
import com.example.myapplication.manager.ui.manage.FourFragment
import com.example.myapplication.manager.ui.manage.OneFragment
import com.example.myapplication.manager.ui.manage.ThreeFragment
import com.example.myapplication.manager.ui.manage.TwoFragment

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.ui.page.manager
 * @ClassName : ManagerFragment
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/7/10 15:34
 * @UpdateUser : yulu
 * @UpdateDate : 2023/7/10 15:34
 * @UpdateRemark : 更新说明
 */
class ManageFragment : BaseFragment() {

    private lateinit var binding: FragmentManagerTestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentManagerTestBinding.inflate(layoutInflater)
        val view = binding.root
        initView(view)
        return view
    }

    private fun initView(view: View) {
        val titles = listOf("CHEER UP", "FEEL SPECIAL", "GO HARD", "T T")
        val fragments = listOf(OneFragment(), TwoFragment(), ThreeFragment(), FourFragment())

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