package com.example.myapplication.shareLifeUser.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.blankj.utilcode.util.ToastUtils
import com.example.myapplication.base.baseUi.BaseFragment
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentPostCommentBinding
import com.example.myapplication.shareLifeUser.vm.NewsDetailViewModel

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.ui.page.home
 * @ClassName : PostCommentFragment
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/7/28 15:33
 * @UpdateUser : yulu
 * @UpdateDate : 2023/7/28 15:33
 * @UpdateRemark : 更新说明
 */
class PostCommentFragment : BaseFragment() {

    private lateinit var binding: FragmentPostCommentBinding
    private var newsId: Long? = null
    private var commentId: Long? = null
    private val viewModel by viewModels<NewsDetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.apply {
            newsId = getLong("newsId")
            commentId = getLong("commentId")
        }
        viewModel.initReplyComment(commentId!!)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPostCommentBinding.inflate(layoutInflater)
        initView()
        return binding.root
    }

    fun initView() {
        binding.replyBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.postButton.setOnClickListener {
            ToastUtils.showShort("发表评论啦")
        }
        binding.replyContentEdit.doOnTextChanged { text, _, _, _ ->
            if (text!!.isNotEmpty()) {
                binding.postButton.apply {
                    setTextColor(resources.getColor(R.color.color_theme))
                    isClickable = true
                }
            } else {
                binding.postButton.apply {
                    setTextColor(resources.getColor(R.color.color_lower_blue))
                    isClickable = false
                }
            }
        }

        viewModel.replyComment.observe(viewLifecycleOwner){comment ->
            binding.postButton.setOnClickListener {
                val content = binding.replyContentEdit.text.toString().trim()
                val level = if (commentId!!.toInt() == -1) 1 else 2
                val replyId = if (commentId!!.toInt() == -1) null else commentId!!
                val replyNumber = if (commentId!!.toInt() == -1) null else comment.number
                    viewModel.postComment(newsId!!, content, level, replyNumber, replyId)
            }
        }


        viewModel.commentState.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().popBackStack()
            }
        }
    }

    companion object {
        fun goPostCommentFragment(
            newsId: Long,
            navController: NavController,
            commentId: Long = -1
        ) {
            val bundle = Bundle().apply {
                putLong("newsId", newsId)
                putLong("commentId", commentId)
            }
            navController.navigate(R.id.goPostCommentFragment, bundle)
        }
    }
}