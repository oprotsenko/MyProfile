package com.protsolo.ui.activityMain

import android.os.Bundle

interface IGoToFragment{
    fun goToFragment(bundle: Bundle, unit: ((Any) -> Any)? = null)
    fun onBackButtonPressed()
}