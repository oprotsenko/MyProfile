package com.protsolo.ui.main.authorization.viewPager

import android.os.Bundle
import android.view.View
import com.google.android.material.tabs.TabLayoutMediator
import com.protsolo.app.base.BaseFragment
import com.protsolo.app.utils.Constants
import com.protsolo.databinding.FragmentViewPagerBinding

class ViewPagerFragment :
    BaseFragment<FragmentViewPagerBinding>(FragmentViewPagerBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = ViewPagerAdapter(this)
        binding.apply {
            viewPager.adapter = adapter

            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = when (position) {
                    0 -> TabSign.SIGN_IN.tabSign
                    else -> TabSign.SIGN_UP.tabSign
                }
            }.attach()
        }
    }

    fun onClick() {
        with(binding.viewPager) {
            currentItem =
                if (currentItem == adapter?.let { it.itemCount - 1 }) 0
                else currentItem + 1
        }
    }

    enum class TabSign {
        SIGN_IN {
            override val tabSign = Constants.SIGN_IN
        },
        SIGN_UP {
            override val tabSign = Constants.SIGN_UP
        };
        abstract val tabSign: String
    }
}
