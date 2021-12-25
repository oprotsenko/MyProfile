package com.protsolo.presentation.main.viewPagerAuthorization

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.protsolo.presentation.main.viewPagerAuthorization.authorization.AuthorizationFragment

class ViewPagerAuthorizationAdapter(val fragment: Fragment) :
    FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return AuthorizationFragment.createFragment(position == 0)
    }
}