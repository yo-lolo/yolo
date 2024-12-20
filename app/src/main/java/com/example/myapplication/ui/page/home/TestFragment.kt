package com.example.myapplication.ui.page.home

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.blankj.utilcode.util.ToastUtils
import com.example.myapplication.Constants
import com.example.myapplication.MainActivity.Companion.TAG
import com.example.myapplication.R
import com.example.myapplication.common.AppConfig
import com.example.myapplication.database.entity.LikeInfo
import com.example.myapplication.database.entity.NotifyInfo
import com.example.myapplication.databinding.FragmentTestBinding
import com.example.myapplication.log.SpeedyLog
import com.example.myapplication.notify.MessNotification
import com.example.myapplication.util.FilePicker
import com.example.myapplication.util.GlideImageLoader
import com.example.myapplication.util.JsonUtil
import com.example.yolo_sdk.tools.FingerprintTool
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions


class TestFragment : Fragment() {

    private lateinit var binding: FragmentTestBinding
    private var mHandler: Handler? = null
    private var mThread: Thread? = null
    private val filePicker = FilePicker(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTestBinding.inflate(inflater)
        initTest()
        return binding.root
    }


    private fun initTest() {

        threadInit()
        binding.initNotify.setOnClickListener {
            MessNotification().showNotify(NotifyInfo(AppConfig.MESS_NOTIFY, "www", "0"))
        }
        binding.filePick.setOnClickListener {
            pickFile(false) { isCancel, uris ->
                SpeedyLog.d(TAG, "isCancel: $isCancel, uris: $uris")
                uris.forEach {
                    GlideImageLoader().displayImageUri(it, binding.pickerImg)
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

    private fun threadInit() {
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
     * 扫一扫
     */
    fun scan() {
        XXPermissions.with(requireActivity())
            .permission(Permission.CAMERA)
            .request(object : OnPermissionCallback {
                override fun onGranted(permissions: MutableList<String>?, all: Boolean) {
                    if (all) {
                        SpeedyLog.i(Constants.BASE_TAG, "相机权限获取成功")
                        // 跳转到扫码界面
                        findNavController().navigate(R.id.goAddNewsFragment)
                    }
                }

                override fun onDenied(permissions: MutableList<String>?, never: Boolean) {
                    if (never) {
                        ToastUtils.showShort("权限被永久拒绝，即将跳转权限设置界面，同意后可以正常使用")
                        Handler().postDelayed({ // 如果是被永久拒绝就跳转到应用权限系统设置页面
                            XXPermissions.startPermissionActivity(requireActivity(), permissions)
                        }, 1000)
                        SpeedyLog.i(
                            Constants.BASE_TAG,
                            permissions.toString() + "权限被永久拒绝，请前往设置界面允许权限"
                        )
                    } else {
                        SpeedyLog.i(Constants.BASE_TAG, permissions.toString() + "相机权限获取失败")
                    }
                }
            })
    }


    override fun onDestroy() {
        super.onDestroy()
        mHandler?.removeCallbacksAndMessages(null)
        mThread?.interrupt()
    }

}