package com.protsolo.app.architecture

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.protsolo.app.utils.Constants
import com.protsolo.ui.main.IGoToFragment

abstract class BaseFragment<T : ViewBinding> : Fragment() {

    protected var navigator: IGoToFragment? = null

    protected lateinit var binding: T
    protected abstract fun getViewBinding(): T


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (Constants.NAV_GRAPH && context is INavigateToFragmentListener) {
            navigator = context
        } else if (context is ITransactionToFragmentListener) {
            navigator = context
        }
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
        navigator = null
    }

    open fun setAnimation() {}

    open fun setUpViews() {}

    open fun setObserver() {}

    open fun setListeners() {}

    private fun init() {
        binding = getViewBinding()
    }
}