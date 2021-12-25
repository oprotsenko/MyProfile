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
    const val FAIL_LOGIN_MESSAGE = "Please register to login"

    // ContactsAdapter
    const val CONTACTS_ITEM_MARGIN = 16

    // Contacts Fragment constants
    const val IS_ADD_CONTACTS_PAGE = "is add contacts page"
    const val UNDO = "UNDO"
    const val SNACK_BAR_ONE_CONTACT_REMOVED_MESSAGE = "'s contact will be removed"
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
    const val CONTACTS = "Contacts"
    const val USERS = "Users"

    // URLs
    const val BASE_URL = "http://188.40.127.78:7777"
    const val POST_REGISTER_URL = "/api/user/register"
    const val POST_AUTHORIZE_URL = "/api/user/login"
    const val POST_REFRESH_TOKEN_URL = "/api/user/auth/refresh"
    const val GET_PROFILE_URL = "/api/user/profile"
    const val POST_EDIT_PROFILE_URL = "/api/user/profile/edit"
    const val GET_ALL_USERS_URL = "/api/users"
    const val POST_ADD_CONTACT_URL = "/api/user/contact/add"
    const val POST_DELETE_CONTACT_URL = "/api/user/contact/delete"
    const val GET_USER_CONTACTS_URL = "/api/user/contacts"

    //Tokens
    const val ACCESS_TOKEN = "access_token"
    const val REFRESH_TOKEN = "refresh_token"
}