package com.protsolo.presentation.main.viewPager.authorization.profile.viewPager

import android.os.Bundle
import android.view.View
import com.google.android.material.tabs.TabLayoutMediator
import com.protsolo.app.base.BaseFragment
import com.protsolo.app.utils.Constants
import com.protsolo.databinding.FragmentViewPagerContactsBinding

class ViewPagerContactsFragment :
    BaseFragment<FragmentViewPagerContactsBinding>(FragmentViewPagerContactsBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = ViewPagerContactsAdapter(this)
        binding.apply {
            viewPagerContacts.adapter = adapter

            TabLayoutMediator(tabLayoutContacts, viewPagerContacts) { tab, position ->
                tab.text = when (position) {
                    0 -> TabSignContacts.CONTACTS.tabSign
                    else -> TabSignContacts.USERS.tabSign
                }
            }.attach()
        }
    }

    fun onPagerItemChange() {
        with(binding.viewPagerContacts) {
            currentItem = if (currentItem == 1) 0 else 1
        }
    }

    enum class TabSignContacts {
        CONTACTS {
            override val tabSign = Constants.CONTACTS
        },
        USERS {
            override val tabSign = Constants.USERS
        };

        abstract val tabSign: String
    }
}