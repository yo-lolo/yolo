package com.example.myapplication.ui.page.home

import android.content.Intent
import android.graphics.Outline
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.blankj.utilcode.util.ToastUtils
import com.ctq.sphone.market.base.BaseFragment
import com.example.myapplication.DataManager
import com.example.myapplication.R
import com.example.myapplication.common.AppConfig
import com.example.myapplication.database.entity.LikeInfo
import com.example.myapplication.database.entity.NotifyInfo
import com.example.myapplication.database.entity.User
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.isLogin
import com.example.myapplication.log.SpeedyLog
import com.example.myapplication.notify.MessNotification
import com.example.myapplication.pops.SelectBrowserPop
import com.example.myapplication.ui.adapter.NewsListAdapter
import com.example.myapplication.util.FilePicker
import com.example.myapplication.util.GlideImageLoader
import com.example.myapplication.util.JsonUtil
import com.example.myapplication.util.createPop
import com.example.myapplication.util.getBrowserList
import com.example.myapplication.util.toBrowser
import com.example.myapplication.util.visibleOrGone
import com.example.myapplication.vm.HomeViewModel
import com.example.yolo_sdk.tools.FingerprintTool

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.ui.page.home
 * @ClassName : HomeFragment
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/6/29 13:44
 * @UpdateUser : yulu
 * @UpdateDate : 2023/6/29 13:44
 * @UpdateRemark : 更新说明
 */
class HomeFragment : BaseFragment() {

    private lateinit var binding: FragmentHomeBinding
    private var newsListAdapter = NewsListAdapter()
    val viewModel by viewModels<HomeViewModel>()
    private var mHandler: Handler? = null
    private var mThread: Thread? = null
    private val filePicker = FilePicker(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        val view = binding.root
        init(view)
        initTest()
        return view
    }

    private fun init(view: View) {

        binding.homeWelcomeLayout.visibleOrGone(isLogin())
        if (isLogin()) {
            binding.textPhone.text = AppConfig.phoneNumber.toString()
        }

        binding.banner.apply {
            setDelayTime(2000) //设置轮播间隔时间
            isAutoPlay(true)  // 设置是否为自动轮播
            setImages(AppConfig.BANNER_IMAGES) //设置图片网址或地址的集合
            setImageLoader(GlideImageLoader()) //设置图片加载器
            setBannerAnimation(com.youth.banner.Transformer.Default) //设置轮播的动画效果，内含多种特效
            outlineProvider = object : ViewOutlineProvider() {
                override fun getOutline(view: View?, outline: Outline?) {
                    // 对父布局进行裁剪 设置圆角radius
                    outline!!.setRoundRect(0, 0, view!!.width, view.height, 20F)
                }
            }
            clipToOutline = true
            start() // 开始轮播
            setOnBannerListener {
                // 轮播图点击监听
                browserPop(AppConfig.BANNER_IMAGES[it])
            }
        }

        // 初始化Recycler
        binding.newsList.apply {
            layoutManager = DataManager.layoutManagerNotScroll()
            adapter = newsListAdapter
        }

        newsListAdapter.goNewsDetailListener = {
            NewsDetailFragment.goNewsDetailFragment(it, findNavController())
        }

        mHandler = object : Handler() {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                if (msg.what == 0) {
                    // 在主线程中拿到消息
                    val test = msg.obj.toString()
                    SpeedyLog.d("fan", "---主线程收到了天气数据---$test");
                    // 解析JSON 将JSON格式复杂的字符串解析成Java对象
                    val testBean = JsonUtil.fromJson(test, LikeInfo::class.java)
                    SpeedyLog.d("fan", "---解析后的数据---" + testBean.toString());
                }
            }
        }

        mThread = Thread {
            val message = Message.obtain()
            message.what = 0
            message.obj = JsonUtil.toJson(LikeInfo(11111, 22222, 2222))
            mHandler?.handleMessage(message)
        }

        mThread?.start()

        viewModel.newsMapData.observe(viewLifecycleOwner) { newsDataList ->
            newsListAdapter.list = newsDataList.take(2)
            newsListAdapter.notifyDataSetChanged()
        }

        binding.goNews.setOnClickListener {
            findNavController().navigate(R.id.goNewsFragment)
        }

        binding.goAddNews.setOnClickListener {
            findNavController().navigate(R.id.goAddNewsFragment)
        }

        context?.let { GlideImageLoader().displayImage(it, R.drawable.world, binding.imageTest) }

    }

    private fun initTest() {
        binding.initNotify.setOnClickListener {
            MessNotification().showNotify(NotifyInfo(AppConfig.MESS_NOTIFY, "www", "0"))
        }
        binding.filePick.setOnClickListener {
            pickFile(false) { isCancel, uris ->
                SpeedyLog.d(TAG, "isCancel: $isCancel, uris: $uris")
                uris.forEach {
                    GlideImageLoader().displayImageUri(it, binding.imageTest)
                }
            }
        }
        binding.fingerCheck.setOnClickListener {
            FingerprintTool.getInstance(requireContext())
                .checkFinger(requireActivity() as AppCompatActivity) { des ->
                    SpeedyLog.d(des)
                    ToastUtils.showShort(des)
                }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == FilePicker.PICK_FILE_REQUEST_CODE) {
            filePicker.onActivityResult(resultCode, data)
        }
    }

    private fun pickFile(isMultipleChoose: Boolean, iPickResult: FilePicker.IPickResult) {
        filePicker.launch(isMultipleChoose, iPickResult)
    }

    /**
     * 指定浏览器打开
     */
    private fun browserPop(jumpUrl: String) {
        val browserList = requireContext().getBrowserList()

        if (browserList.size == 1) {
            val uri = Uri.parse(jumpUrl)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            requireContext().startActivity(intent)
            return
        }

        SelectBrowserPop(requireContext(), browserList).apply {
            this.onSelectBrowser = {
                if (jumpUrl != null) {
                    requireContext().toBrowser(it, jumpUrl)
                }
            }
        }.createPop {
            this.enableDrag(true)
        }.show()
    }

    override fun onResume() {
        super.onResume()
        viewModel.initData()
    }

    override fun onDestroy() {
        super.onDestroy()
        mHandler?.removeCallbacksAndMessages(null)
        mThread?.interrupt()
    }

}