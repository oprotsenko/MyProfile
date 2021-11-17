package com.protsolo.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.NavHostFragment
import com.protsolo.R
import com.protsolo.app.App
import com.protsolo.app.utils.Constants.NAV_GRAPH
import com.protsolo.ui.main.authorization.profile.contacts.adapters.ContactsViewHolder
import com.protsolo.ui.main.navigators.NavigateToFragment
import com.protsolo.ui.main.navigators.TransactionToFragment

class MainActivity : AppCompatActivity(), IGoToFragment {

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

    override fun goToFragment(bundle: Bundle, unit: ((Array<*>) -> Any)?) {
        val direction = { fragmentDirection: Array<*> ->
            when (navigator) {
                is NavigateToFragment -> navigateToFragment(fragmentDirection)
                else -> transactionToFragment(fragmentDirection)
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

    private fun transactionToFragment(fragmentDirection: Array<*>): Int {
        val fragmentManager = supportFragmentManager.beginTransaction()
        setAnimation(fragmentManager)
        return fragmentManager.addToBackStack(null).replace(
            R.id.container,
            fragmentDirection[0] as Fragment
        ).commit()
    }

    private fun navigateToFragment(fragmentDirection: Array<*>) {
        if (fragmentDirection.size == 1) {
            navController.navigate(fragmentDirection[0] as NavDirections)
        } else {
            navController.navigate(
                fragmentDirection[0] as NavDirections,
                ContactsViewHolder.extras
            )
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