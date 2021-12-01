package com.protsolo.ui.main.authorization.viewPager

import android.os.Bundle
import android.view.View
import com.google.android.material.tabs.TabLayoutMediator
import com.protsolo.app.architecture.BaseFragment
import com.protsolo.databinding.FragmentViewPagerBinding

class ViewPagerFragment : BaseFragment<FragmentViewPagerBinding>(FragmentViewPagerBinding::inflate),
    IViewPagerListener {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = ViewPagerAdapter(this)
        binding.apply {
            viewPager.adapter = adapter

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

    override fun onClick() {
        with(binding.viewPager) {
            currentItem =
                if (currentItem == adapter?.let { it.itemCount - 1 }) 0
                else currentItem + 1
        }
    }
}