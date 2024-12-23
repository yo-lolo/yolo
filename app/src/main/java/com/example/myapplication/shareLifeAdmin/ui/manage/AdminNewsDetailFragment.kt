package com.example.myapplication.shareLifeAdmin.ui.manage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.myapplication.base.baseUi.BaseFragment
import com.example.myapplication.R
import com.example.myapplication.base.database.entity.NewsInfo
import com.example.myapplication.databinding.AdminFragmentNewsDetailBinding
import com.example.myapplication.util.TimeUtil

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.ui.page.home
 * @ClassName : NewsDetailFragment
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/7/27 17:27
 * @UpdateUser : yulu
 * @UpdateDate : 2023/7/27 17:27
 * @UpdateRemark : 更新说明
 */
class AdminNewsDetailFragment : BaseFragment() {

    private lateinit var binding: AdminFragmentNewsDetailBinding

    var newsInfo: NewsInfo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        newsInfo = arguments?.getSerializable("news") as NewsInfo
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AdminFragmentNewsDetailBinding.inflate(layoutInflater)
        initView()
        return binding.root
    }

    private fun initView() {
        binding.include.headLayout.apply {
            setBackListener {
                findNavController().popBackStack()
            }
            setTitle("文章详情")
            setHeadLayoutColor()
        }
        binding.apply {
            newsDetailAuthor.text = "账号：" + newsInfo!!.number.toString()
            newsDetailContent.text = "内容：\n" + newsInfo!!.content.repeat(500)
            newsDetailTag.text = newsInfo!!.tag
            newsDetailTime.text = "发布时间：" + TimeUtil.millis2String(newsInfo!!.time)
            newsDetailTitle.text = "标题：" + newsInfo!!.title

        }

    }

    companion object {
        fun goAdminNewsDetailFragment(newsInfo: NewsInfo, navController: NavController) {
            val args = Bundle().apply {
                putSerializable("news", newsInfo)
            }
            navController.navigate(R.id.goAdminNewsDetailFragment, args)
        }
    }
}