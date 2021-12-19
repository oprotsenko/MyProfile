package com.protsolo.data.remote

import com.protsolo.app.utils.Constants
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST

interface IServiceApi {

    @Headers("Content-Type: miltipart/form-data")
    @Multipart
    @POST(Constants.POST_REGISTER_URL)
    suspend fun register(
        @Body registerUserRequest: RegisterUserRequest,
    ): Call<RegisterResponse>

    @POST(Constants.POST_AUTHORIZE_URL)
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>
}