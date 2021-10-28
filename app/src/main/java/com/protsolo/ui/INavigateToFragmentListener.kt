package com.protsolo.ui

import android.os.Bundle

interface INavigateToFragmentListener {
    fun onNavigateToFragment(fragmentTag: String, args: Bundle)
    fun onBackButtonPressed()
}