package com.example.myapplication.shareLifeUser.ui.mine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.myapplication.base.baseUi.BaseFragment
import com.example.myapplication.R
import com.example.myapplication.chargeToastLogin
import com.example.myapplication.common.Constants
import com.example.myapplication.databinding.FragmentUserDetailBinding
import com.example.myapplication.shareLifeUser.ui.mess.ChatFragment
import com.example.myapplication.util.GlideImageLoader
import com.example.myapplication.util.TimeUtil
import com.example.myapplication.shareLifeUser.vm.UserDetailViewModel

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.ui.page.mine
 * @ClassName : UserDetailFragmet
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/7/31 14:04
 * @UpdateUser : yulu
 * @UpdateDate : 2023/7/31 14:04
 * @UpdateRemark : 更新说明
 */
class UserDetailFragment : BaseFragment() {

    private lateinit var binding: FragmentUserDetailBinding
    var account: Long? = null
    val viewModel by viewModels<UserDetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        account = arguments?.getLong(Constants.ACCOUNT)
        viewModel.initUserInfo(account!!)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserDetailBinding.inflate(layoutInflater)
        initView()
        return binding.root
    }

    private fun initView() {

        binding.include.headLayout.apply {
            setBackListener {
                findNavController().popBackStack()
            }
            setMenuListener {}
        }

        // 判断是否为好友
        viewModel.isFriend.observe(viewLifecycleOwner) {
            binding.goChat.visibility = if (it) View.VISIBLE else View.GONE
            binding.addFriend.visibility = if (it) View.GONE else View.VISIBLE
        }

        viewModel.userDetailInfo.observe(viewLifecycleOwner) {
            binding.userDetailNumber.text = "电话号码：${it.number.toString()}"
            binding.userDetailNeck.text = it.neck
            binding.userDetailTime.text =
                "注册时间：${TimeUtil.millis2String(it.time, TimeUtil.dateFormatYMD_CN)}"
            GlideImageLoader().displayLocalFile(it.image, binding.userIcon)
        }

        binding.goChat.setOnClickListener {
            ChatFragment.goChatFragment(findNavController(), account!!)
        }
        binding.addFriend.setOnClickListener {
            chargeToastLogin {
                viewModel.insertFriend(account!!)
            }
        }
        binding.userNews.setOnClickListener {
            MineNewsFragment.goMineNewsFragment(findNavController(), account!!, 1)
        }
    }

    companion object {
        fun goUserDetailFragment(account: Long, navController: NavController) {
            var args = Bundle().apply {
                putLong(Constants.ACCOUNT, account)
            }
            navController.navigate(R.id.goUserDetailFragment, args)
        }
    }
}