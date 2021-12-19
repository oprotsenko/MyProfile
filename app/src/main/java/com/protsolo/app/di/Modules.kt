package com.protsolo.app.di

import com.protsolo.app.utils.PreferenceStorage
import com.protsolo.app.utils.Validator
import com.protsolo.ui.main.viewPager.authorization.AuthorizationViewModel
import com.protsolo.ui.main.viewPager.authorization.profile.ProfileViewModel
import com.protsolo.ui.main.viewPager.authorization.signUp.SignUpViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModules = module {

    viewModel {
        AuthorizationViewModel(get(), get(), get())
    }

    viewModel {
        ProfileViewModel(get())
    }

    viewModel {
        SignUpViewModel(get(), get())
    }

    single { PreferenceStorage(androidContext()) }
    single { Validator() }
}
