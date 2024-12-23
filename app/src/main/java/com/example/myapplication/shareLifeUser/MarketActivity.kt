package com.example.myapplication.shareLifeUser

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.myapplication.R
import com.example.myapplication.base.baseUi.BaseActivity
import com.example.myapplication.common.AppConfig
import com.example.myapplication.base.cache.MMKVManager
import com.example.myapplication.base.database.entity.ChatInfo
import com.example.myapplication.base.database.entity.NotifyInfo
import com.example.myapplication.databinding.ActivityMarketBinding
import com.example.myapplication.isLogin
import com.example.myapplication.shareLifeUser.ui.mess.ChatFragment
import com.example.myapplication.util.PromptUtils
import com.example.myapplication.util.JsonUtil

/**
 * @Copyright : China Telecom Quantum Technology Co.,Ltd
 * @ProjectName : My Application
 * @Package : com.example.myapplication.ui
 * @ClassName : MarketActivity
 * @Description : 文件描述
 * @Author : yulu
 * @CreateDate : 2023/7/7 16:37
 * @UpdateUser : yulu
 * @UpdateDate : 2023/7/7 16:37
 * @UpdateRemark : 更新说明
 */
class MarketActivity : BaseActivity() {

    private lateinit var navController: NavController
    private lateinit var binding: ActivityMarketBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMarketBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initNav()
    }

    private fun initNav() {
        // Navigation相关文章 https://developer.android.google.cn/guide/navigation/navigation-getting-started?hl=zh-cn
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        binding.bottomNav.setupWithNavController(navController)

        // 在此判断用户是否未登出且开启自动登录功能 是的话跳转到登陆界面完成自动登录
        if (isLogin() && MMKVManager.isAutoLogin) {
            navController.navigate(
                R.id.goLoginFragment,
                Bundle().apply { putBoolean("isMarket2Login", true) })
        } else if (!isLogin()) {
            // 如果未登录 弹窗询问是否要登录
            PromptUtils().prompt("您还未登录，是否要先登录？") {
                navController.navigate(R.id.goLoginFragment)
            }
        }
        //底部tab隐藏控制
        navController.addOnDestinationChangedListener { _, destination, _ ->
            val isMainTab = listOf(
                R.id.homeFragment,
                R.id.messFragment,
                R.id.mineFragment
            ).contains(destination.id)
            binding.bottomNav.visibility = if (isMainTab) View.VISIBLE else View.GONE
        }
    }

    fun initNotifyIntent() {
        val data = intent.getSerializableExtra("data") as NotifyInfo?
        when (data?.tag) {
            AppConfig.MESS_NOTIFY -> {
                val chatInfo = JsonUtil.fromJson(data.jsonData, ChatInfo::class.java)
                chatInfo?.sender?.let { ChatFragment.goChatFragment(navController, it) }
            }

            AppConfig.AUDIT_NOTIFY -> {
                navController.navigate(R.id.goMineNewsFragment)
            }

            AppConfig.FRIEND_APPLY_NOTIFY -> {
                navController.navigate(R.id.goNewFriendsFragment)
            }

            else -> {
                //do nothing
            }
        }
    }
}