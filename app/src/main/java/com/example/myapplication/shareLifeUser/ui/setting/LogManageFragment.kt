package com.example.myapplication.shareLifeUser.ui.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.ZipUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.myapplication.base.baseUi.BaseFragment
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentLogManageBinding
import com.example.myapplication.databinding.LayoutShareHeadBinding
import com.example.myapplication.base.log.SpeedyLog
import com.example.myapplication.base.log.XLogUtil
import com.example.myapplication.util.ShareUtil
import com.lxj.xpopup.XPopup
import java.io.File


/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName :
 * @Package : My Application
 * @ClassName : com.example.myapplication.ui.page.setting
 * @Description : 文件描述
 * @Author : Yulu
 * @CreateDate : 2024/1/10 10:03
 * @UpdateUser : Yulu
 * @UpdateDate : 2024/1/10 10:03
 * @UpdateRemark : 更新说明
 */
class LogManageFragment : BaseFragment() {

    private lateinit var binding: FragmentLogManageBinding
    private lateinit var shareHeadBinding: LayoutShareHeadBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLogManageBinding.inflate(layoutInflater)
        shareHeadBinding = LayoutShareHeadBinding.inflate(layoutInflater)
        initView()
        return binding.root
    }

    private fun initView() {
        binding.include.headLayout.apply {
            setTitle("日志管理")
            setBackListener { findNavController().popBackStack() }
            bindRightView(shareHeadBinding.root)
            setRightViewClickListener {
                doShareAppLog()
            }
        }

        val appLogFolder = File(XLogUtil.defaultLogFolderPath)
        if (appLogFolder.exists() && appLogFolder.isDirectory) {
            val appLogFiles = appLogFolder.listFiles().toList()
            initAppLogRecyclerView(appLogFiles)
            SpeedyLog.d(
                TAG,
                "app 当前可读取的日志文件：${
                    appLogFiles.joinToString(
                        prefix = "[",
                        postfix = "]"
                    ) { it.name }
                }"
            )
        }

    }

    /**
     * 转发分享
     */
    private fun doShareAppLog() {
        val logFolders = mutableListOf<File>()
        val appLogFolder = File(XLogUtil.defaultLogFolderPath)
        val xCrashFileFolder = File(XLogUtil.defaultCrashFolderPath)
        logFolders.add(appLogFolder)
        logFolders.add(xCrashFileFolder)
        val externalFilesDir = requireContext().getExternalFilesDir(null)
        val logZipFile = File(externalFilesDir, "log.zip")

        val loadingPopupView = XPopup.Builder(requireContext()).asLoading("正在压缩日志文件")
        loadingPopupView.show()

        SpeedyLog.d(TAG, "开始压缩日志文件夹： $logFolders")
        ZipUtils.zipFiles(logFolders, logZipFile)
        SpeedyLog.d(TAG, "压缩日志文件夹完成： $logZipFile")

        loadingPopupView.dismiss()

        if (logZipFile.exists()) {
            SpeedyLog.d(TAG, "日志压缩文件存在， 开始分享")
            ShareUtil.shareFile(requireContext(), logZipFile.absolutePath)
        }
    }

    /**
     * 初始化日志文件布局
     */
    private fun initAppLogRecyclerView(appLogFiles: List<File>) {
        val appLogFilesSorted = appLogFiles.sortedBy { it.name }

        val appLogAdapter = object : BaseQuickAdapter<File, BaseViewHolder>(
            R.layout.item_log_file,
            appLogFilesSorted.toMutableList()
        ) {

            override fun convert(holder: BaseViewHolder, item: File) {
                holder.getView<TextView>(R.id.item_log_file_name).text = item.name
            }
        }

        appLogAdapter.setOnItemClickListener { _, _, pos ->
            val args = Bundle().apply {
                putSerializable("logFile", appLogFilesSorted[pos])
            }
            findNavController().navigate(R.id.goLogViewFragment, args)
        }

        binding.appLogRecyclerView.run {
            this.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            this.adapter = appLogAdapter
        }
    }

}