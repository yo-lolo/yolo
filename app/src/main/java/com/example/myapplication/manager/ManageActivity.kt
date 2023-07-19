package com.example.myapplication.manager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.myapplication.R
import com.example.myapplication.base.BaseActivity
import com.example.myapplication.databinding.ActivityManageBinding

class ManageActivity : BaseActivity() {

    private lateinit var binding: ActivityManageBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityManageBinding.inflate(layoutInflater)
        val view = binding.root
        initNav()
        setContentView(view)
    }

    /**
     * 初始化导航栏
     */
    private fun initNav() {
        // 初始化导航宿主NavHostFragment 有相关联的导航图
        // 相关文章 https://developer.android.google.cn/guide/navigation/navigation-getting-started?hl=zh-cn
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_manage_fragment) as NavHostFragment
        navController = navHostFragment.navController

        binding.bottomNav.setupWithNavController(navController)

        //底部tab隐藏控制
        navController.addOnDestinationChangedListener { _, destination, _ ->
            val isMainTab = listOf(
                R.id.managerFragment,
                R.id.adminFragment
            ).contains(destination.id)
            binding.bottomNav.visibility = if (isMainTab) View.VISIBLE else View.GONE
        }
    }

}