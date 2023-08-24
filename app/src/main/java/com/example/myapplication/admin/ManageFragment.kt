package com.example.myapplication.admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ctq.sphone.market.base.BaseFragment
import com.example.myapplication.DataManager
import com.example.myapplication.admin.adpter.FilesAdapter
import com.example.myapplication.databinding.FragmentManagerTestBinding

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

    private lateinit var binding: FragmentManagerTestBinding
    private var filesAdapter = FilesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentManagerTestBinding.inflate(layoutInflater)
        val view = binding.root
        initView(view)
        return view
    }

    private fun initView(view: View) {
        binding.files.apply {
            layoutManager = DataManager.GridLayoutManagerNotScroll(3)
            adapter = filesAdapter
        }

        filesAdapter.clickListener = {
            findNavController().navigate(it)
        }
    }
}