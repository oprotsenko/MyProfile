package com.protsolo.app.utils

object Constants {

    const val PHONEBOOK_CONTACTS = false
    const val USER_BUNDLE_KEY = "USER"

    // Preferences keys
    const val PREFERENCE_EMAIL_KEY = "email"
    const val PREFERENCE_PASSWORD_KEY = "password"
    const val PREFERENCE_AUTOLOGIN = "autologin"

    // Authorization constants
    const val IS_LOGIN_PAGE = "is login page"
    const val PASSWORD_PATTERN = "[a-z0-9?:!#$%&]{8,}"

    // ContactsAdapter
    const val CONTACTS_ITEM_MARGIN = 16

    // Contacts Fragment constants
    const val UNDO = "UNDO"
    const val SNACK_BAR_ONE_CONTACT_REMOVED_MESSAGE = "'s contact has been removed"
    const val SNACK_BAR_SELECTED_CONTACTS_REMOVED_MESSAGE = " contacts has been removed"

    // Custom View
    const val DEFAULT_TEXT_SIZE = 12f

    // Add contact dialog fragment
    const val REQUEST_CODE = 1
    const val READ_FILE_TYPE = "image/*"
    const val PERMISSION_NEEDED = "The app needs your permission, open phone settings to allow it."

    // ViewPager
    const val SIGN_IN = "Sign in"
    const val SIGN_UP = "Sign up"
}