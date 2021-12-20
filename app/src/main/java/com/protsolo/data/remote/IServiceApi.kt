package com.protsolo.data.remote

import com.protsolo.app.utils.Constants
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface IServiceApi {

    @Headers("Content-Type: miltipart/form-data")
    @Multipart
    @POST(Constants.POST_REGISTER_URL)
    suspend fun register(
        @Part email: MultipartBody.Part,
        @Part pass: MultipartBody.Part,
        @Part name: MultipartBody.Part? = null,
        @Part phone: MultipartBody.Part? = null,
        @Part career: MultipartBody.Part? = null,
        @Part address: MultipartBody.Part? = null,
        @Part image: MultipartBody.Part? = null,
    ): Call<RegisterResponse>

    @Headers("Content-Type: miltipart/form-data | application/json")
    @POST(Constants.POST_AUTHORIZE_URL)
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>
}