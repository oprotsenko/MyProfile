package com.protsolo.app.di

import com.protsolo.data.IRepository
import com.protsolo.data.remote.IMyProfileApi
import com.protsolo.data.remote.IRemoteDataSource
import com.protsolo.data.remote.retrifit.RetrofitMyProfileDataSource
import com.protsolo.domain.MyProfileRepository
import org.koin.dsl.module

val remoteModule = module {

    single { provideMyProfileRepository(get()) }
    single { provideRemoteDataSource(get()) }
}

fun provideMyProfileRepository(
    remote: IRemoteDataSource
) : IRepository = MyProfileRepository(remote)

fun provideRemoteDataSource(remoteData: IMyProfileApi) : IRemoteDataSource =
    RetrofitMyProfileDataSource(remoteData)