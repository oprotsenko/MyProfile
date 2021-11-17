package com.protsolo.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.protsolo.R
import com.protsolo.app.App

class MainActivity : AppCompatActivity() {

//    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        lastNonConfigurationInstance
//        initNavController()
    }

    override fun onRetainCustomNonConfigurationInstance(): Any {
        return this
    }

    override fun onDestroy() {
        super.onDestroy()
        App.isFirstLogin = false
    }

//    private fun navigateToFragment(fragmentDirection: Array<*>) {
//        if (fragmentDirection.size == 1) {
//            navController.navigate(fragmentDirection[0] as NavDirections)
//        } else {
//            navController.navigate(
//                fragmentDirection[0] as NavDirections,
//                ContactsViewHolder.extras
//            )
//        }
//    }


//    private fun initNavController() {
//        val navHostFragment =
//            supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment
//        navController = navHostFragment.navController
//    }
}