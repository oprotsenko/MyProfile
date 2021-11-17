package com.protsolo.app.architecture

import android.content.Context
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

    protected lateinit var navController: NavController

    protected lateinit var binding: T
    protected abstract fun getViewBinding(): T


    override fun onAttach(context: Context) {
        super.onAttach(context)
        initNavController()
//        if (context is IGoToFragment) {
//            navigator = context
//        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        setAnimation()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()
        setObserver()
        setListeners()
    }

    override fun onDetach() {
        super.onDetach()
//        navigator = null
    }

    open fun setAnimation() {}

    open fun setUpViews() {}

    open fun setObserver() {}

    open fun setListeners() {}

    private fun init() {
        binding = getViewBinding()
    }

    private fun initNavController() {
        val navHostFragment =
            parentFragmentManager.findFragmentById(R.id.container) as NavHostFragment
        navController = navHostFragment.navController
    }
}