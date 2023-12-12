package com.example.myapplication

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ctq.sphone.market.base.BaseFragment
import com.example.myapplication.databinding.FragmentSplashBinding

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName :
 * @Package : My Application
 * @ClassName : com.example.myapplication
 * @Description : 文件描述
 * @Author : Yulu
 * @CreateDate : 2023/12/12 10:13
 * @UpdateUser : Yulu
 * @UpdateDate : 2023/12/12 10:13
 * @UpdateRemark : 更新说明
 */
class SplashFragment : BaseFragment() {

    private lateinit var binding : FragmentSplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSplashBinding.inflate(layoutInflater)
        val view = binding.root
        initView()
        return view
    }

    private fun initView() {
        //欢迎语动画设置
        ValueAnimator.ofFloat(0.0F, 1.0F).also { animator ->
            animator.repeatMode = ValueAnimator.RESTART
            animator.duration = 2000L
            animator.addUpdateListener {
                binding.typerTextView.setProgress(it.animatedValue as Float)
            }
        }.start()
    }
}