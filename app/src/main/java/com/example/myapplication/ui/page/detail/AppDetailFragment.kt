package com.example.myapplication.ui.page.detail

import android.os.Bundle
import androidx.navigation.NavController
import com.ctq.sphone.market.base.BaseFragment
import com.example.myapplication.R
import com.example.myapplication.database.entity.ApkInfo

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.ui.page.detail
 * @ClassName : AppDetailFragment
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/6/29 16:27
 * @UpdateUser : yulu
 * @UpdateDate : 2023/6/29 16:27
 * @UpdateRemark : 更新说明
 */
class AppDetailFragment: BaseFragment() {


    companion object{
        fun goAppDetailFragment(navController: NavController, apkInfo: ApkInfo) {
            goAppDetailFragment(
                navController, apkInfo.desc, apkInfo.desc
            )
        }

        private fun goAppDetailFragment(
            navController: NavController,
            appId: String,
            packageName: String
        ) {
            val args = Bundle().apply {
                putString("appId", appId)
                putString("packageName", packageName)
            }
            navController.navigate(R.id.goAppDetailFragment, args)
        }
    }


}
