package com.protsolo.ui.activityMain.navigators

import android.os.Bundle
import com.protsolo.ui.activityMain.ITransactionToFragmentListener
import com.protsolo.ui.authorization.AuthorizationFragment
import com.protsolo.ui.contactDetailView.ContactDetailViewFragment
import com.protsolo.ui.contacts.ContactsFragment
import com.protsolo.ui.mainPage.MainPageFragment
import com.protsolo.utils.Constants

class TransactionToFragment : ITransactionToFragmentListener {
    override fun goToFragment(bundle: Bundle, unit: ((Array<*>) -> Any)?) {
        when (bundle.getString(Constants.FRAGMENT_BUNDLE_KEY)) {
            Constants.MAIN_FRAGMENT -> {
                unit?.let { it(arrayOf(MainPageFragment.newInstance(bundle))) }
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