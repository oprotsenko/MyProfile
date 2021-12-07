package com.protsolo.app.di

import com.protsolo.app.utils.*
import com.protsolo.ui.main.authorization.AuthorizationViewModel
import com.protsolo.ui.main.authorization.profile.ProfileViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModules = module {

    viewModel {
        AuthorizationViewModel(get())
    }

    viewModel {
        ProfileViewModel(get())
    }

    single { PreferenceStorage(androidContext()) }
    single { SelectionItemView() }
    scope<Validator> { Validator() }
    scope<MeasureUtils> { MeasureUtils() }
    scope<CustomViewUtils> { CustomViewUtils() }
    scope<IntentUtils> { IntentUtils() }
}
