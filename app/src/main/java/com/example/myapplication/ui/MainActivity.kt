package com.example.myapplication.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        activityMainBinding.bottomNav.setupWithNavController(navController)

        //底部tab隐藏控制
        navController.addOnDestinationChangedListener  { _, destination, _ ->
            val isMainTab = listOf(
                R.id.homeFragment,
                R.id.detailFragment,
                R.id.mineFragment
            ).contains(destination.id)
            activityMainBinding.bottomNav.visibility = if (isMainTab) View.VISIBLE else View.GONE
        }
    }
}