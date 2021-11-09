package com.protsolo.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.NavHostFragment
import com.protsolo.App
import com.protsolo.R
import com.protsolo.ui.viewPager.ViewPagerFragment
import com.protsolo.utils.Constants.NAV_GRAPH

class ActivityMain : AppCompatActivity(), INavigateToFragmentListener {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (NAV_GRAPH) {
            initNavController()
        }
        lastNonConfigurationInstance
        if (lastNonConfigurationInstance == null) {
            if (NAV_GRAPH) {
                navController.navigate(R.id.viewPagerFragmentNav)
            } else {
                onTransactionTo(ViewPagerFragment())
            }
        }
    }

    override fun onRetainCustomNonConfigurationInstance(): Any {
        return this
    }

    override fun onDestroy() {
        super.onDestroy()
        App.isFirstLogin = false
    }

    override fun onNavigateTo(action: NavDirections) {
        navController.navigate(action)
    }

    override fun onTransactionTo(fragment: Fragment) {
        supportFragmentManager.beginTransaction().addToBackStack(null).replace(
            R.id.container,
            fragment
        ).commit()
    }

    override fun onBackButtonPressed() {
        if (NAV_GRAPH) {
            navController.popBackStack()
        } else {
            if (supportFragmentManager.backStackEntryCount > 0)
                supportFragmentManager.popBackStack()
        }
    }

    private fun initNavController() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment
        navController = navHostFragment.navController
    }

    companion object {
        var isLoginPage = true
    }
}