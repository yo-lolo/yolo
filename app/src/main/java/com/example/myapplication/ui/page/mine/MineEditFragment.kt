package com.example.myapplication.ui.page.mine

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.blankj.utilcode.util.UriUtils
import com.ctq.sphone.market.base.BaseFragment
import com.example.myapplication.config.AppConfig
import com.example.myapplication.databinding.MineEditLayoutBinding
import com.example.myapplication.util.GlideImageLoader
import com.example.myapplication.util.TimeUtil
import com.example.myapplication.vm.MineViewModel

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.ui.page.mine
 * @ClassName : MineEditFragment
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/8/23 14:39
 * @UpdateUser : yulu
 * @UpdateDate : 2023/8/23 14:39
 * @UpdateRemark : 更新说明
 */
class MineEditFragment : BaseFragment() {

    private lateinit var binding: MineEditLayoutBinding
    val viewModel by viewModels<MineViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel.initData()
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MineEditLayoutBinding.inflate(layoutInflater)
        val view = binding.root
        initView(view)
        return view
    }

    private fun initView(view: View) {

        binding.replyBack.setOnClickListener {
            findNavController().popBackStack()
        }

        viewModel.user.observe(viewLifecycleOwner) { user ->
            binding.apply {
                mineAddress.text = Editable.Factory.getInstance().newEditable(user.address)
                minePersonalSign.text = Editable.Factory.getInstance().newEditable(user.sign)
                GlideImageLoader().displayImageWithRadius(user.image, mineImage)
                mineNeck.text = Editable.Factory.getInstance().newEditable(user.neck)
                mineNumber.text = user.number.toString()
                mineRegisterTime.text = TimeUtil.millis2String(user.time, TimeUtil.dateFormatYMD_CN)
                viewModel.imagePath.value = user.image
                mineImage.setOnClickListener {
                    openPhoto()
                }
                mineOk.setOnClickListener {
                    val neck = mineNeck.text.toString().trim()
                    val address = mineAddress.text.toString().trim()
                    val sign = minePersonalSign.text.toString().trim()
                    viewModel.updateUserEdit(neck, address, sign)
                }
            }
        }
    }

    /**
     * 打开相册选择图片
     * 调用startActivityForResult方法向系统选择器请求数据 第二个参数是请求码
     */
    private fun openPhoto() {
        val intent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        startActivityForResult(intent, AppConfig.USER_IMAGE_EDIT)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        // 判断结果和请求码是否正确 再判断数据是否为空
        if (resultCode == AppCompatActivity.RESULT_OK && requestCode == AppConfig.USER_IMAGE_EDIT) {
            if (data != null) {
                // 解析数据
                val uri = data.data
                val photoPath = UriUtils.uri2File(uri).absolutePath
                viewModel.refreshPath(photoPath)
                GlideImageLoader().displayLocalFile(photoPath, binding.mineImage)
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}