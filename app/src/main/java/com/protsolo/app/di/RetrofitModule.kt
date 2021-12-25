package com.protsolo.app.di

import com.protsolo.app.utils.Constants
import com.protsolo.app.utils.PreferenceStorage
import com.protsolo.data.remote.IMyProfileApi
import com.protsolo.data.remote.retrifit.RetrofitMyProfileDataSource
import com.protsolo.domain.MyProfileRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val retrofitModule = module {
    single { provideInterceptor() }
    single { provideOkHttpClient(get(), get()) }
    single { provideRetrofit(get()) }
    single { provideDefinitionApi(get()) }
    single { RetrofitMyProfileDataSource(get()) }
    single { MyProfileRepository(get()) }
}

fun provideInterceptor(): HttpLoggingInterceptor {
    val interceptor = HttpLoggingInterceptor()
    return interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
}

fun provideOkHttpClient(
    interceptor: HttpLoggingInterceptor,
    preferenceStorage: PreferenceStorage
): OkHttpClient =
    OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request().newBuilder().addHeader(
                "Authorization",
                "Bearer ${preferenceStorage.getString(Constants.ACCESS_TOKEN).orEmpty()}"
            )
                .build()
            return@addInterceptor chain.proceed(request)
        }
        .addInterceptor(interceptor).build()

fun provideRetrofit(client: OkHttpClient): Retrofit =
    Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

fun provideDefinitionApi(retrofit: Retrofit): IMyProfileApi =
    retrofit.create(IMyProfileApi::class.java)