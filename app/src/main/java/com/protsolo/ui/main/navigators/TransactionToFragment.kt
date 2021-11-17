package com.protsolo.ui.main.navigators

import android.os.Bundle
import com.protsolo.app.utils.Constants
import com.protsolo.ui.main.IGoToFragment
import com.protsolo.ui.main.authorization.AuthorizationFragment
import com.protsolo.ui.main.authorization.profile.ProfileFragment
import com.protsolo.ui.main.authorization.profile.contacts.ContactsFragment
import com.protsolo.ui.main.authorization.profile.contacts.details.ContactDetailViewFragment

class TransactionToFragment : IGoToFragment {
    override fun goToFragment(bundle: Bundle, unit: ((Array<*>) -> Any)?) {
        when (bundle.getString(Constants.FRAGMENT_BUNDLE_KEY)) {
            Constants.MAIN_FRAGMENT -> {
                unit?.let { it(arrayOf(ProfileFragment.newInstance(bundle))) }
            }
            Constants.CONTACTS_FRAGMENT -> {
                unit?.let { it(arrayOf(ContactsFragment())) }
            }
            Constants.DETAIL_VIEW_FRAGMENT -> {
                unit?.let { it(arrayOf(ContactDetailViewFragment.newInstance(bundle))) }
            }
            else -> {
                unit?.let { it(arrayOf(AuthorizationFragment.newInstance(bundle))) }
            }
        }
    }

    override fun onBackButtonPressed() {
    }
}