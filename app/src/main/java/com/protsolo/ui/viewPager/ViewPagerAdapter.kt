package com.protsolo.ui.viewPager

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.protsolo.ui.ActivityMain
import com.protsolo.ui.authorization.AuthorizationFragment

class ViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
       return when(position) {
            1 -> {
                ActivityMain.isLoginPage = true
                AuthorizationFragment()
            }
            else -> {
                ActivityMain.isLoginPage = false
                AuthorizationFragment()
            }
        }
    }
}
