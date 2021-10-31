package com.protsolo.ui.base

import android.os.Bundle

interface INavigateToFragmentListener {
    fun onNavigateToFragment(
        fragmentToNavigate: Any,
        args: Bundle? = null,
    )

    fun onBackButtonPressed()
}