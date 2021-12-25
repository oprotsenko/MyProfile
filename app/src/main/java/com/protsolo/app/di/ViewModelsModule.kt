package com.protsolo.app.di

import com.protsolo.app.utils.PreferenceStorage
import com.protsolo.app.utils.Validator
import com.protsolo.presentation.main.viewPager.authorization.AuthorizationViewModel
import com.protsolo.presentation.main.viewPager.authorization.profile.ProfileViewModel
import com.protsolo.presentation.main.viewPager.authorization.profile.edit.EditProfileViewModel
import com.protsolo.presentation.main.viewPager.authorization.profile.viewPager.contacts.ContactsViewModel
import com.protsolo.presentation.main.viewPager.authorization.profile.viewPager.users.UsersViewModel
import com.protsolo.presentation.main.viewPager.authorization.signUp.SignUpViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModules = module {

    viewModel {
        AuthorizationViewModel(get(), get(), get())
    }

    viewModel {
        ProfileViewModel(get(), get(), get())
    }

    viewModel {
        SignUpViewModel(get(), get())
    }

    viewModel {
        ContactsViewModel(get())
    }

    viewModel {
        UsersViewModel(get())
    }

    viewModel {
        EditProfileViewModel(get())
    }

    single { PreferenceStorage(androidContext()) }
    single { Validator() }
}
