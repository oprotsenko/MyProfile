package com.protsolo.data.remote.retrifit

import com.protsolo.data.remote.IMyProfileApi
import com.protsolo.data.remote.requests.EditProfileRequest
import com.protsolo.data.remote.requests.LoginRequest
import com.protsolo.data.remote.requests.RegisterUserRequest
import com.protsolo.data.remote.responses.*
import com.protsolo.data.remote.IRemoteDataSource
import retrofit2.Call

class RetrofitMyProfileDataSource(private val myProfileApi: IMyProfileApi) : IRemoteDataSource {

    override fun register(request: RegisterUserRequest): Call<AuthorizeResponse> =
        myProfileApi.register(request)

    override fun login(request: LoginRequest): Call<AuthorizeResponse> =
        myProfileApi.login(request)

    override fun getProfile(): Call<ProfileResponse> =
        myProfileApi.getProfile()

    override fun editProfile(request: EditProfileRequest): Call<ProfileResponse> =
        myProfileApi.editProfile(request)

    override fun getContacts(): Call<ContactsResponse> =
        myProfileApi.getContacts()

    override fun getAllUsers(): Call<UsersResponse> =
        myProfileApi.getAllUsers()

    override fun addContact(contactId: Int): Call<ContactsResponse> =
        myProfileApi.addContact(contactId)

    override fun deleteContact(contactId: Int): Call<ContactsResponse> =
        myProfileApi.deleteContact(contactId)

    override fun refreshToken(refreshToken: String): Call<RefreshTokenResponse> =
        myProfileApi.refreshToken(refreshToken)
}