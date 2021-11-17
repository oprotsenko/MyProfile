package com.protsolo.ui.main.authorization.viewPager

import com.google.android.material.tabs.TabLayoutMediator
import com.protsolo.app.architecture.BaseFragment
import com.protsolo.databinding.FragmentViewPagerBinding

class ViewPagerFragment : BaseFragment<FragmentViewPagerBinding>() {

    override fun getViewBinding(): FragmentViewPagerBinding =
        FragmentViewPagerBinding.inflate(layoutInflater)

    override fun setUpViews() {
        super.setUpViews()

        val viewPagerAdapter = ViewPagerAdapter(this)
        val viewPager = binding.viewPager
        viewPager.adapter = viewPagerAdapter
        val tabLayout = binding.tabLayout
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = "Sign ${
                when (position) {
                    0 -> "in"
                    else -> "up"
                }
            }"
        }.attach()
    }
}