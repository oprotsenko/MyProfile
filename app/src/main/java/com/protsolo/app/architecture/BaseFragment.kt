package com.protsolo.app.architecture

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.viewbinding.ViewBinding
import com.protsolo.R

abstract class BaseFragment<T : ViewBinding> : Fragment() {

    protected lateinit var binding: T
    protected abstract fun getViewBinding(): T

    protected lateinit var navigator: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        initNavController()
        setAnimation()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    open fun setAnimation() {}

    private fun init() {
        binding = getViewBinding()
    }

    private fun initNavController() {
        val navHostFragment =
            requireActivity().supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment
        navigator = navHostFragment.navController
    }
}