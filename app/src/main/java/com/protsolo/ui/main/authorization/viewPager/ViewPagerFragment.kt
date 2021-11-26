package com.protsolo.ui.main.authorization.viewPager

import android.os.Bundle
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.protsolo.app.architecture.BaseFragment
import com.protsolo.databinding.FragmentViewPagerBinding

class ViewPagerFragment : BaseFragment<FragmentViewPagerBinding>(), IViewPagerListener {

    lateinit var adapter: ViewPagerAdapter
    private lateinit var viewPager: ViewPager2

    override fun getViewBinding(): FragmentViewPagerBinding =
        FragmentViewPagerBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = ViewPagerAdapter(this)
        viewPager = binding.viewPager
        viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, viewPager) { tab, position ->
            tab.text = "Sign ${
                when (position) {
                    0 -> "in"
                    else -> "up"
                }
            }"
        }.attach()
    }

    override fun onClick() {
        viewPager.currentItem =
            if (viewPager.currentItem == adapter.itemCount - 1) 0
            else viewPager.currentItem + 1
    }
}