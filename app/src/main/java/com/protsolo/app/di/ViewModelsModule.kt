package com.protsolo.app.di

import com.protsolo.app.utils.PreferenceStorage
import com.protsolo.app.utils.Validator
import com.protsolo.presentation.main.viewPagerAuthorization.authorization.AuthorizationViewModel
import com.protsolo.presentation.main.viewPagerAuthorization.authorization.profile.ProfileViewModel
import com.protsolo.presentation.main.viewPagerAuthorization.authorization.profile.edit.EditProfileViewModel
import com.protsolo.presentation.main.viewPagerAuthorization.authorization.profile.viewPagerContacts.contacts.ContactsViewModel
import com.protsolo.presentation.main.viewPagerAuthorization.authorization.profile.viewPagerContacts.users.UsersViewModel
import com.protsolo.presentation.main.viewPagerAuthorization.authorization.signUp.SignUpViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModules = module {

    viewModel {
        AuthorizationViewModel(get(), get(), get())
    }

    viewModel {
        SignUpViewModel(get(), get())
    }

    viewModel {
        ProfileViewModel(get(), get())
    }

    viewModel {
        EditProfileViewModel(get())
    }

    viewModel {
        ContactsViewModel(get(), get(), get())
    }

    viewModel {
        UsersViewModel(get(), get())
    }

    single { PreferenceStorage(androidContext()) }
    single { Validator() }
}