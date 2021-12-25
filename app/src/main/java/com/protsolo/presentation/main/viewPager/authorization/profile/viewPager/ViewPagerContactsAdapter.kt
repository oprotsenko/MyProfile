package com.protsolo.presentation.main.viewPager.authorization.profile.viewPager

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.protsolo.presentation.main.viewPager.authorization.profile.viewPager.contacts.ContactsFragment
import com.protsolo.presentation.main.viewPager.authorization.profile.viewPager.users.UsersFragment

class ViewPagerContactsAdapter (val fragment: Fragment) :
    FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> ContactsFragment()
            else -> UsersFragment()
        }
    }
}