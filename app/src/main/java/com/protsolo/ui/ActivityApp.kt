package com.protsolo.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.NavHostFragment
import com.protsolo.R
import com.protsolo.ui.authorization.AuthorizationFragment
import com.protsolo.ui.base.INavigateToFragmentListener
import com.protsolo.ui.contactDetailView.ContactDetailViewFragment
import com.protsolo.ui.contactsList.ContactsListFragment
import com.protsolo.ui.login.LoginFragment
import com.protsolo.ui.mainPage.MainPageFragment
import com.protsolo.utils.Constants
import com.protsolo.utils.GlobalVal

var FIRST_LOGIN = true

class ActivityApp : AppCompatActivity(), INavigateToFragmentListener {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app)
        if (GlobalVal.NAV_GRAPH) {
            runNavigationGraph()
        } else {
            runFragmentTransactions()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        FIRST_LOGIN = true
    }

    override fun onNavigateToFragment(fragmentToNavigate: Any, args: Bundle?) {
        when (fragmentToNavigate) {
            is NavDirections -> navController.navigate(fragmentToNavigate)
            else -> {
                if (args != null) {
                    val fragment =
                        with(Constants) {
                            when (fragmentToNavigate) {
                                DETAIL_VIEW -> ContactDetailViewFragment.newInstance(args)
                                CONTACTS_LIST -> ContactsListFragment.newInstance(args)
                                MAIN_PAGE -> MainPageFragment.newInstance(args)
                                AUTHORIZATION -> AuthorizationFragment.newInstance(args)
                                else -> LoginFragment()
                            }
                        }
                    supportFragmentManager.beginTransaction().addToBackStack(null).replace(
                        R.id.container,
                        fragment
                    ).commit()
                }
            }
        }
    }

    override fun onBackButtonPressed() {
        if (GlobalVal.NAV_GRAPH) {
            navController.popBackStack()
        } else {
            if (supportFragmentManager.backStackEntryCount > 0)
                supportFragmentManager.popBackStack()
        }
    }

    private fun runFragmentTransactions() {
        val fragment = LoginFragment()
        supportFragmentManager.beginTransaction()
            .addToBackStack(Constants.MY_PROFILE_BACK_STACK)
            .add(R.id.container, fragment)
            .commit()
    }

    private fun runNavigationGraph() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment
        navController = navHostFragment.navController
        navController.navigate(R.id.loginFragment)
    }
}