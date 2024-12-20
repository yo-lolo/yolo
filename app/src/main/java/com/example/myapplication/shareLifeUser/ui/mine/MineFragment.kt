package com.example.myapplication.shareLifeUser.ui.mine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myapplication.base.baseUi.BaseFragment
import com.example.myapplication.R
import com.example.myapplication.chargeToastLogin
import com.example.myapplication.databinding.FragmentMineBinding
import com.example.myapplication.isLogin
import com.example.myapplication.util.GlideImageLoader
import com.example.myapplication.util.visibleOrGone
import com.example.myapplication.shareLifeUser.vm.MineViewModel

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.ui.page.mine
 * @ClassName : MineFragment
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/6/29 13:44
 * @UpdateUser : yulu
 * @UpdateDate : 2023/6/29 13:44
 * @UpdateRemark : 更新说明
 */
class MineFragment : BaseFragment() {


    private lateinit var homeBinding: FragmentMineBinding
    val viewModel by viewModels<MineViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeBinding = FragmentMineBinding.inflate(layoutInflater)
        val view = homeBinding.root
        init(view)
        return view
    }

    fun init(view: View) {

        homeBinding.apply {
            userName.setOnClickListener {
                findNavController().navigate(R.id.goLoginFragment)
            }
            mineEdit.visibleOrGone(isLogin())
            mineEdit.setOnClickListener {
                findNavController().navigate(R.id.goMineEditFragment)
            }
            goMineComment.setOnClickListener {
                chargeToastLogin {
                    findNavController().navigate(R.id.goMineCommentsFragment)
                }
            }
            goMineNews.setOnClickListener {
                chargeToastLogin {
                    MineNewsFragment.goMineNewsFragment(findNavController())
                }
            }
            goLogManage.setOnClickListener {
                findNavController().navigate(R.id.goLogManageFragment)
            }
            goMineLikes.setOnClickListener {
                chargeToastLogin {
                    findNavController().navigate(R.id.goMineLikeFragment)
                }
            }
            goSetting.setOnClickListener {
                findNavController().navigate(R.id.goSettingFragment)
            }
            goFeedback.setOnClickListener {
                chargeToastLogin {
                    findNavController().navigate(R.id.goFeedbackFragment)
                }
            }
        }

        viewModel.user.observe(viewLifecycleOwner) { user ->
            if (isLogin() && user != null) {
                homeBinding.userName.text = user.neck
                homeBinding.userName.isEnabled = false
                GlideImageLoader().displayLocalFile(user.image, homeBinding.mineTouxiang)
                homeBinding.mineTouxiang.setOnClickListener {
                    UserDetailFragment.goUserDetailFragment(user.number, findNavController())
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.initData()
    }

}