package com.protsolo.app.di

import com.protsolo.domain.useCases.*
import org.koin.dsl.module

val useCasesModule = module {

    single { LoginUseCase(get()) }
    single { RegisterUseCase(get()) }
    single { GetContactsUseCase(get()) }
    single { DeleteContactUseCase(get()) }
    single { AddContactUseCase(get()) }
    single { EditProfileUseCase(get()) }
    single { GetAllUsersUseCase(get()) }
    single { GetProfileUseCase(get()) }
}