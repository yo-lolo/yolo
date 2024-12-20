package com.example.myapplication.base.baseUi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import com.example.myapplication.getTag
import com.example.myapplication.view.ProgressDialog


/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : safe-phone
 * @Package : com.ctq.sphone.market.base
 * @ClassName : 类名
 * @Description : 文件描述
 * @Author : hulifei
 * @CreateDate : 2023/1/10 11:30
 * @UpdateUser : hulifei
 * @UpdateDate : 2023/1/10 11:30
 * @UpdateRemark : 更新说明
 */
open class BaseFragment : Fragment() {

    lateinit var progressDialog: ProgressDialog

    protected val TAG = (this as Any).getTag()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    fun initProgress(loadingTaskCount: LiveData<Int>) {

        loadingTaskCount.observe(viewLifecycleOwner) {

            if (!this::progressDialog.isInitialized) {
                progressDialog = ProgressDialog(requireContext())
                progressDialog.setCancelable(false)
                progressDialog.setCanceledOnTouchOutside(false)
            }

            if (it > 0) {
                if (!progressDialog.isShowing) {
                    progressDialog.show()
                }
            } else {
                if (progressDialog.isShowing) {
                    progressDialog.cancel()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (this::progressDialog.isInitialized) {
            if (progressDialog.isShowing) {
                progressDialog.cancel()
            }
        }
    }

}