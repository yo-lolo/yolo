package com.example.myapplication.shareLifeUser.ui.home

import android.annotation.SuppressLint
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.myapplication.DataManager
import com.example.myapplication.base.baseUi.BaseFragment
import com.example.myapplication.R
import com.example.myapplication.chargeToastLogin
import com.example.myapplication.databinding.FragmentNewsDetailBinding
import com.example.myapplication.shareLifeUser.adapter.CommentsAdapter
import com.example.myapplication.shareLifeUser.adapter.EmptyViewAdapter
import com.example.myapplication.shareLifeUser.ui.mine.UserDetailFragment
import com.example.myapplication.util.PromptUtils
import com.example.myapplication.util.GlideImageLoader
import com.example.myapplication.util.TimeUtil
import com.example.myapplication.shareLifeUser.vm.NewsDetailViewModel

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
    private var commentListAdapter = CommentsAdapter()
    var newsId: Long? = null
    private val viewModel by viewModels<NewsDetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        newsId = arguments?.getLong("newsId")
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

    @SuppressLint("SetTextI18n")
    private fun initView() {
        binding.include.headLayout.apply {
            setBackListener {
                findNavController().popBackStack()
            }
            setHeadLayoutColor()
        }
        viewModel.userInfo.observe(viewLifecycleOwner) { user ->
            binding.newsDetailAuthor.text = user.neck
            GlideImageLoader().displayLocalFile(user.image, binding.userIcon)
        }
        // 初始化截面数据
        viewModel.newsInfo.observe(viewLifecycleOwner) { newsInfo ->
            binding.apply {
                newsMess.setOnClickListener {
                    // 判断文章作者是否为好友 跳转到用户详情界面
                    UserDetailFragment.goUserDetailFragment(
                        newsInfo.number,
                        findNavController()
                    )
                }
                newsDetailContent.text = newsInfo.content.repeat(100)
                newsDetailTag.text = newsInfo.tag
                newsDetailTime.text =
                    TimeUtil.millis2String(newsInfo.time, TimeUtil.dateFormatYMD_CN)
                newsDetailTitle.text = newsInfo.title
                // TODO: 文章编辑功能需添加更新时间字段
                newsDetailUpdateTime.text =
                    "-----     更新于 ${TimeUtil.millis2String(newsInfo.time)}     -----"
            }
        }

        viewModel.isLike.observe(viewLifecycleOwner) { isLike ->
            val colorRes = if (isLike) R.color.candy_r else R.color.color_8a8a8a
            val colorStateList = ContextCompat.getColorStateList(DataManager.context, colorRes)
            binding.fabLike.apply {
                imageTintMode = PorterDuff.Mode.SRC_ATOP
                imageTintList = colorStateList
            }
        }
        binding.fabLike.setOnClickListener {
            chargeToastLogin {
                viewModel.chargeLike(newsId!!)
            }
        }

        binding.postComment.setOnClickListener {
            chargeToastLogin {
                PostCommentFragment.goPostCommentFragment(newsId!!, findNavController())
            }
        }

        viewModel.contentCount.observe(viewLifecycleOwner) {
            binding.contentCount.text = it.toString()
        }

        viewModel.isFriend.observe(viewLifecycleOwner) {
            binding.addFriend.visibility = if (it) View.GONE else View.VISIBLE
        }

        binding.addFriend.setOnClickListener {
            chargeToastLogin {
                viewModel.insertFriend(newsId!!)
            }
        }
        initCommentList()

        initProgress(viewModel.loadingTaskCount)
    }

    /**
     * 初始化评论列表
     */
    private fun initCommentList() {
        binding.commentList.apply {
            layoutManager = DataManager.layoutManagerNotScroll()
            adapter = EmptyViewAdapter(commentListAdapter)
        }
        commentListAdapter.goCommentListener = {
            PostCommentFragment.goPostCommentFragment(newsId!!, findNavController(), it)
        }
        commentListAdapter.goUserDetail = {
            UserDetailFragment.goUserDetailFragment(it, findNavController())
        }
        commentListAdapter.deleteCommentListener = {
            PromptUtils().deletePrompt("'${it.content}'这条评论") {
                viewModel.deleteComment(newsId!!, it)
            }
        }
        viewModel.commentsMap.observe(viewLifecycleOwner) {
            commentListAdapter.list = it
            commentListAdapter.notifyDataSetChanged()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.initData(newsId!!)
    }

    companion object {
        fun goNewsDetailFragment(newsId: Long, navController: NavController) {
            val args = Bundle().apply {
                putLong("newsId", newsId)
            }
            navController.navigate(R.id.goNewsDetailFragment, args)
        }
    }
}