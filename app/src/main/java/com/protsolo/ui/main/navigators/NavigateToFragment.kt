package com.protsolo.ui.main.navigators

import android.os.Bundle
import com.protsolo.app.utils.Constants
import com.protsolo.data.models.UserModel
import com.protsolo.ui.main.IGoToFragment
import com.protsolo.ui.main.authorization.AuthorizationFragmentDirections
import com.protsolo.ui.main.authorization.profile.ProfileFragmentDirections
import com.protsolo.ui.main.authorization.profile.contacts.ContactsFragmentDirections

class NavigateToFragment : IGoToFragment {
    override fun goToFragment(bundle: Bundle, unit: ((Array<*>) -> Any)?) {
        when (bundle.getString(Constants.FRAGMENT_BUNDLE_KEY)) {
            Constants.MAIN_FRAGMENT -> {
                val email = bundle.getString(Constants.PREFERENCE_EMAIL_KEY)
                unit?.let {
                    it(
                        arrayOf(
                            AuthorizationFragmentDirections.actionAuthorizationFragmentToProfileFragment(email)
                        )
                    )
                }
            }
            Constants.CONTACTS_FRAGMENT -> {
                unit?.let { it(arrayOf(ProfileFragmentDirections.actionProfileFragmentToContactsFragment())) }
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
                unit?.let { it(arrayOf(ContactsFragmentDirections.actionContactsFragmentToAddContactDialogFragment())) }
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