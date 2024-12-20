package com.example.myapplication.shareLifeUser.ui.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.myapplication.base.baseUi.BaseFragment
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentPapBinding

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.ui.page.setting
 * @ClassName : PAPFragment
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/7/19 14:07
 * @UpdateUser : yulu
 * @UpdateDate : 2023/7/19 14:07
 * @UpdateRemark : 更新说明
 */
class PAPFragment : BaseFragment() {

    lateinit var binding: FragmentPapBinding
    private var path: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        path = arguments?.getString("path")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPapBinding.inflate(layoutInflater)
        val view = binding.root
        initView(view)
        return view
    }


    private fun initView(view: View) {

        binding.imageExit.setOnClickListener{
            findNavController().popBackStack()
        }

        binding.webView.apply {
            settings.javaScriptEnabled = true //开启javascript 渲染
            webViewClient = WebViewClient()
            loadUrl(path!!) //载入内容
        }
    }

    companion object {
        fun goPAPFragment(navController: NavController, path: String) {
            val args = Bundle().apply {
                putString("path", path)
            }
            navController.navigate(R.id.goPAPFragment, args)
        }
    }
}