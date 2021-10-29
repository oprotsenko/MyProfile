package com.protsolo.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.protsolo.R
import com.protsolo.ui.authorization.AuthorizationFragment
import com.protsolo.ui.contactDetailView.ContactDetailViewFragment
import com.protsolo.ui.contactsList.ContactsListFragment
import com.protsolo.ui.mainPage.MainPageFragment
import com.protsolo.utils.Constants

class ActivityApp : AppCompatActivity(), INavigateToFragmentListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app)
        if (savedInstanceState == null) {
            val fragment = AuthorizationFragment()
            supportFragmentManager.beginTransaction().addToBackStack(Constants.MY_PROFILE_BACK_STACK)
                .add(R.id.container, fragment)
                .commit()
        }
    }

    override fun onNavigateToFragment(fragmentTag: String, args: Bundle) {
        val fragment =
            with(Constants) {
                when (fragmentTag) {
                    DETAIL_VIEW -> ContactDetailViewFragment.newInstance(args)
                    CONTACTS_LIST -> ContactsListFragment.newInstance(args)
                    MAIN_PAGE -> MainPageFragment.newInstance(args)
                    else -> AuthorizationFragment()
                }
            }
        supportFragmentManager.beginTransaction().addToBackStack(null).replace(
            R.id.container,
            fragment
        ).commit()
    }

    override fun onBackButtonPressed() {
        if (supportFragmentManager.backStackEntryCount > 0)
            supportFragmentManager.popBackStack()
    }
}