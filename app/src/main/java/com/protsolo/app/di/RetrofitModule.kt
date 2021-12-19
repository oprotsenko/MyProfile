package com.protsolo.app.di

import com.protsolo.app.utils.Constants
import com.protsolo.data.remote.IServiceApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val retrofitModule = module {
    single { provideInterceptor() }
    single { provideOkHttpClient(get()) }
    single { provideRetrofit(get()) }
    single { provideDefinitionApi(get()) }
}

fun provideInterceptor(): HttpLoggingInterceptor {
    val interceptor = HttpLoggingInterceptor()
    return interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
}

fun provideOkHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient =
    OkHttpClient.Builder()

//        .addInterceptor { chain ->
//            val request = chain.request().body?.let {
//                chain.request().newBuilder()
//                    .patch(it).build()
//            }
//            return@addInterceptor chain.proceed(request as Request)
//        }
        .addInterceptor(interceptor).build()

fun provideRetrofit(client: OkHttpClient): Retrofit =
    Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

fun provideDefinitionApi(retrofit: Retrofit): IServiceApi =
    retrofit.create(IServiceApi::class.java)