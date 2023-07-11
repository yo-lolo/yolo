package com.example.myapplication.ui.page.manager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityManageBinding

class ManageActivity : AppCompatActivity() {

    private lateinit var binding : ActivityManageBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityManageBinding.inflate(layoutInflater)
        val view = binding.root
        initNav()
        setContentView(view)
    }

    private fun initNav() {
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