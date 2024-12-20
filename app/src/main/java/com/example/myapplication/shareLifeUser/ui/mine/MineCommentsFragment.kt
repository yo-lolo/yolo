package com.example.myapplication.shareLifeUser.ui.mine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.base.baseUi.BaseFragment
import com.example.myapplication.databinding.FragmentMineCommentsBinding
import com.example.myapplication.shareLifeUser.adapter.EmptyViewAdapter
import com.example.myapplication.shareLifeUser.adapter.MineCommentListAdapter
import com.example.myapplication.shareLifeUser.ui.home.NewsDetailFragment
import com.example.myapplication.shareLifeUser.ui.home.PostCommentFragment
import com.example.myapplication.shareLifeUser.vm.MineCommentsViewModel

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
class MineCommentsFragment : BaseFragment() {

    private lateinit var binding: FragmentMineCommentsBinding
    val viewModel by viewModels<MineCommentsViewModel>()
    private val commentsMineAdapter = MineCommentListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.initComments()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMineCommentsBinding.inflate(layoutInflater)
        val view = binding.root
        initView(view)
        return view
    }

    private fun initView(view: View) {
        binding.include.headLayout.apply {
            setTitle("评论")
            setBackListener { findNavController().popBackStack() }
            setHeadLayoutColor()
        }

        viewModel.commentsNewsMap.observe(viewLifecycleOwner) {
            commentsMineAdapter.list = it
            commentsMineAdapter.notifyDataSetChanged()
        }

        binding.mineComments.apply {
            adapter = EmptyViewAdapter(commentsMineAdapter)
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }

        commentsMineAdapter.goCommentListener = { newsId, commentId ->
            PostCommentFragment.goPostCommentFragment(newsId, findNavController(), commentId)
        }
        commentsMineAdapter.goNewsDetailListener = {
            NewsDetailFragment.goNewsDetailFragment(it, findNavController())
        }
        commentsMineAdapter.goUserDetailListener = {
            UserDetailFragment.goUserDetailFragment(it, findNavController())
        }
        initProgress(viewModel.loadingTaskCount)
    }

}