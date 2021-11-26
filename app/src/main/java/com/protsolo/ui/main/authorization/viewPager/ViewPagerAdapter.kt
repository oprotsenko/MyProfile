package com.protsolo.ui.main.authorization.viewPager

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.protsolo.ui.main.authorization.AuthorizationFragment

class ViewPagerAdapter(val fragment: Fragment) :
    FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            1 -> {
                AuthorizationFragment.isLoginPage = false
                AuthorizationFragment.onPagerClickListener = fragment as IViewPagerListener
                AuthorizationFragment()
            }
            else -> {
                AuthorizationFragment.isLoginPage = true
                AuthorizationFragment.onPagerClickListener = fragment as IViewPagerListener
                AuthorizationFragment()
            }
        }
    }
}
