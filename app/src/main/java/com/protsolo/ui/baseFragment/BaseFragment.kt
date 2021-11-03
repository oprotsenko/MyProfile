package com.protsolo.ui.baseFragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.protsolo.App
import com.protsolo.utils.extensions.hideKeyboard

abstract class BaseFragment<T : ViewBinding> : Fragment() {

    protected val preferenceStorage = App().getStoragePreferences()
    protected val args: Bundle = Bundle()

    protected var listener: INavigateToFragmentListener? = null

    protected lateinit var binding: T
    protected abstract fun getViewBinding(): T


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is INavigateToFragmentListener) {
            listener = context
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        initViewModel()
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
        setListeners()
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    open fun initViewModel() {}

    open fun setUpViews() {}

    open fun setListeners() {}

    private fun init() {
        binding = getViewBinding()
        binding.root.setOnClickListener {
            binding.root.hideKeyboard()
        }
    }
}