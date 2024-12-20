package com.example.myapplication.shareLifeAdmin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.myapplication.DataManager
import com.example.myapplication.base.baseUi.BaseFragment
import com.example.myapplication.shareLifeAdmin.adpter.FilesAdapter
import com.example.myapplication.databinding.FragmentManagerBinding

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

    private lateinit var binding: FragmentManagerBinding
    private var filesAdapter = FilesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentManagerBinding.inflate(layoutInflater)
        val view = binding.root
        initView(view)
        return view
    }

    private fun initView(view: View) {
        binding.files.apply {
            layoutManager = DataManager.gridLayoutManagerNotScroll(3)
            adapter = filesAdapter
        }

        filesAdapter.clickListener = {
            findNavController().navigate(it)
        }
    }
}