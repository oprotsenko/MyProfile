package com.protsolo.domain

import com.protsolo.data.IRepository
import com.protsolo.data.remote.IRemoteDataSource
import com.protsolo.data.remote.requests.EditProfileRequest
import com.protsolo.data.remote.requests.LoginRequest
import com.protsolo.data.remote.requests.RegisterUserRequest
import com.protsolo.data.remote.responses.*
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.Response

class MyProfileRepository(private val remote: IRemoteDataSource) : IRepository  {
    override fun register(request: RegisterUserRequest): Call<AuthorizeResponse> =
        remote.register(request)

    override fun login(request: LoginRequest): Call<AuthorizeResponse> =
        remote.login(request)

    override fun getProfile(): Call<ProfileResponse> =
        remote.getProfile()

    override fun editProfile(request: EditProfileRequest): Call<ProfileResponse> =
        remote.editProfile(request)

    override suspend fun getContacts(): Flow<Response<ContactsResponse>> =
        remote.getContacts()

    override fun getAllUsers(): Call<UsersResponse> =
        remote.getAllUsers()

    override fun addContact(contactId: Int): Call<ContactsResponse> =
        remote.addContact(contactId)

    override fun deleteContact(contactId: Int): Call<ContactsResponse> =
        remote.deleteContact(contactId)

    override fun refreshToken(refreshToken: String): Call<RefreshTokenResponse> =
        remote.refreshToken(refreshToken)

}