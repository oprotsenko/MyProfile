package com.protsolo.ui

import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections

interface INavigateToFragmentListener {
    fun onNavigateTo(action: NavDirections)
    fun onTransactionTo(fragment: Fragment)
    fun onBackButtonPressed()
}