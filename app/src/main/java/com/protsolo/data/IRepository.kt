package com.protsolo.data

import com.protsolo.data.remote.requests.EditProfileRequest
import com.protsolo.data.remote.requests.LoginRequest
import com.protsolo.data.remote.requests.RegisterUserRequest
import com.protsolo.data.remote.responses.*
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.Response

interface IRepository {
    fun register(request: RegisterUserRequest): Call<AuthorizeResponse>
    fun login(request: LoginRequest): Call<AuthorizeResponse>
    fun getProfile(): Call<ProfileResponse>
    fun editProfile(request: EditProfileRequest): Call<ProfileResponse>
    suspend fun getContacts(): Flow<Response<ContactsResponse>>
//    fun getContacts(): Call<ContactsResponse>
    fun getAllUsers(): Call<UsersResponse>
    fun addContact(contactId: Int): Call<ContactsResponse>
    fun deleteContact(contactId: Int): Call<ContactsResponse>
    fun refreshToken(refreshToken: String): Call<RefreshTokenResponse>
}