package com.example.myapplication.ui.page.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.ctq.sphone.market.base.BaseFragment
import com.example.myapplication.DataManager
import com.example.myapplication.R
import com.example.myapplication.database.entity.NewsInfo
import com.example.myapplication.databinding.FragmentNewsDetailBinding
import com.example.myapplication.ui.adapter.CommentListAdapter
import com.example.myapplication.ui.adapter.EmptyViewAdapter
import com.example.myapplication.ui.page.mine.UserDetailFragment
import com.example.myapplication.useCase.PromptUseCase
import com.example.myapplication.util.TimeUtil
import com.example.myapplication.vm.NewsDetailViewModel

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
class NewsDetailFragment : BaseFragment() {

    private lateinit var binding: FragmentNewsDetailBinding
    private var commentListAdapter = CommentListAdapter()
    var newsInfo: NewsInfo? = null
    private val viewModel by viewModels<NewsDetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        newsInfo = arguments?.getSerializable("news") as NewsInfo
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsDetailBinding.inflate(layoutInflater)
        initView()
        return binding.root
    }

    private fun initView() {
        binding.include.headLayout.apply {
            setBackListener {
                findNavController().popBackStack()
            }
            setMenuListener {}
            setHeadLayoutColor()
        }
        binding.apply {
            newsDetailAuthor.text = newsInfo!!.number.toString()
            newsAuthorIcon.setOnClickListener {
                // 判断文章作者是否为好友 跳转到用户详情界面
                val tag = if (viewModel.isFriend.value!!) 1 else 0
                UserDetailFragment.goUserDetailFragment(
                    tag,
                    newsInfo!!.number,
                    findNavController()
                )
            }
            newsDetailContent.text = newsInfo!!.content.repeat(500)
            newsDetailTag.text = newsInfo!!.tag
            newsDetailTime.text = TimeUtil.millis2String(newsInfo!!.time)
            newsDetailTitle.text = newsInfo!!.title
            // TODO:文章管理，添加更新时间字段
            newsDetailUpdateTime.text =
                "-----     更新于 ${TimeUtil.millis2String(newsInfo!!.time)}     -----"
            commentList.apply {
                layoutManager = DataManager.layoutManagerNotScroll()
                adapter = EmptyViewAdapter(commentListAdapter)
            }
            binding.postComment.setOnClickListener {
                PostCommentFragment.goPostCommentFragment(newsInfo!!.id, findNavController())
            }

            viewModel.comments.observe(viewLifecycleOwner) {
                commentListAdapter.allList = it
                commentListAdapter.notifyDataSetChanged()
            }

            commentListAdapter.goCommentListener = {
                PostCommentFragment.goPostCommentFragment(newsInfo!!.id, findNavController(), it)
            }
            commentListAdapter.goUserDetail = {
                UserDetailFragment.goUserDetailFragment(it, findNavController())
            }
            commentListAdapter.deleteCommentListener = {
                PromptUseCase().deletePrompt("确定要删除这条评论吗"){
                    viewModel.deleteComment(newsInfo!!, it)
                }
            }
            viewModel.isFriend.observe(viewLifecycleOwner) {
                binding.addFriend.visibility = if (it) View.GONE else View.VISIBLE
            }

            binding.addFriend.setOnClickListener {
                viewModel.insertFriend(newsInfo!!)
            }
        }
        initProgress(viewModel.loadingTaskCount)
    }

    override fun onResume() {
        super.onResume()
        viewModel.initData(newsInfo!!)
    }

    companion object {
        fun goNewsDetailFragment(newsInfo: NewsInfo, navController: NavController) {
            val args = Bundle().apply {
                putSerializable("news", newsInfo)
            }
            navController.navigate(R.id.goNewsDetailFragment, args)
        }
    }
}