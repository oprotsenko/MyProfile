package com.protsolo.ui.main

import android.os.Bundle

interface IGoToFragment{
    fun goToFragment(bundle: Bundle, unit: ((Array<*>) -> Any)? = null)
    fun onBackButtonPressed()
}