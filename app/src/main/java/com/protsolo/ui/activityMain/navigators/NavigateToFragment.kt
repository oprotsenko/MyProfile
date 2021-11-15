package com.protsolo.ui.activityMain.navigators

import android.os.Bundle
import com.protsolo.itemModel.UserModel
import com.protsolo.ui.activityMain.INavigateToFragmentListener
import com.protsolo.ui.authorization.AuthorizationFragmentDirections
import com.protsolo.ui.contacts.ContactsFragmentDirections
import com.protsolo.ui.mainPage.MainPageFragmentDirections
import com.protsolo.utils.Constants

class NavigateToFragment : INavigateToFragmentListener {
    override fun goToFragment(bundle: Bundle, unit: ((Array<*>) -> Any)?) {
        when (bundle.getString(Constants.FRAGMENT_BUNDLE_KEY)) {
            Constants.MAIN_FRAGMENT -> {
                val email = bundle.getString(Constants.PREFERENCE_EMAIL_KEY)
                unit?.let {
                    it(
                        arrayOf(
                            AuthorizationFragmentDirections.actionAuthorizationFragmentToMainPageFragment(
                                email
                            )
                        )
                    )
                }
            }
            Constants.CONTACTS_FRAGMENT -> {
                unit?.let { it(arrayOf(MainPageFragmentDirections.actionMainPageFragmentToContactsListFragment())) }
            }
            Constants.DETAIL_VIEW_FRAGMENT -> {
                val user = bundle.getParcelable<UserModel>(Constants.USER_BUNDLE_KEY)
                unit?.let {
                    it(
                        arrayOf(
                            ContactsFragmentDirections.actionContactsFragmentToContactDetailViewFragment(
                                user
                            ),
                            0
                        )
                    )
                }
            }
            Constants.ADD_CONTACT_DIALOG_FRAGMENT -> {
                unit?.let { it(arrayOf(ContactsFragmentDirections.actionContactsListFragmentToAddContactDialogFragment())) }
            }
            else -> {
                val email = bundle.getString(Constants.PREFERENCE_EMAIL_KEY)
                val pass = bundle.getString(Constants.PREFERENCE_PASSWORD_KEY)
                unit?.let {
                    it(
                        arrayOf(
                            AuthorizationFragmentDirections.actionAuthorizationFragmentNavSelf(
                                email,
                                pass
                            )
                        )
                    )
                }
            }
        }
    }

    override fun onBackButtonPressed() {
    }
}