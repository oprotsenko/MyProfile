package com.protsolo.ui.activityMain

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.NavHostFragment
import com.protsolo.App
import com.protsolo.R
import com.protsolo.ui.activityMain.navigators.NavigateToFragment
import com.protsolo.ui.activityMain.navigators.TransactionToFragment
import com.protsolo.utils.Constants.NAV_GRAPH

class ActivityMain : AppCompatActivity(), INavigateToFragmentListener,
    ITransactionToFragmentListener {

    private lateinit var navigator: IGoToFragment
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        lastNonConfigurationInstance
        navigator = if (NAV_GRAPH) {
            initNavController()
            NavigateToFragment()
        } else {
            TransactionToFragment()
        }
    }

    override fun onRetainCustomNonConfigurationInstance(): Any {
        return this
    }

    override fun onDestroy() {
        super.onDestroy()
        App.isFirstLogin = false
    }

    override fun goToFragment(bundle: Bundle, unit: ((Any) -> Any)?) {
        val direction = { navigator: Any ->
            when (navigator) {
                is NavDirections -> navController.navigate(navigator)
                is Fragment -> {
                    val fragmentManager = supportFragmentManager.beginTransaction()
                    setAnimation(fragmentManager)
                    fragmentManager.addToBackStack(null).replace(
                        R.id.container,
                        navigator
                    ).commit()
                }
                else -> {}
            }
        }
        navigator.goToFragment(bundle, direction)
    }

    override fun onBackButtonPressed() {
        if (NAV_GRAPH) {
            navController.popBackStack()
        } else {
            if (supportFragmentManager.backStackEntryCount > 0)
                supportFragmentManager.popBackStack()
        }
    }

    private fun setAnimation(fragmentManager: FragmentTransaction) {
        fragmentManager.setCustomAnimations(
            R.anim.slide_in_right,
            R.anim.slide_out_left,
            R.anim.slide_in_left,
            R.anim.slide_out_right
        )
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