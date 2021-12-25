package com.protsolo.data.remote.retrifit

import com.protsolo.data.remote.IMyProfileApi
import com.protsolo.data.remote.IRemoteDataSource
import com.protsolo.data.remote.requests.EditProfileRequest
import com.protsolo.data.remote.requests.LoginRequest
import com.protsolo.data.remote.requests.RegisterUserRequest
import com.protsolo.data.remote.responses.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Call
import retrofit2.Response

class RetrofitMyProfileDataSource(private val myProfileApi: IMyProfileApi) : IRemoteDataSource {

    override fun register(request: RegisterUserRequest): Call<AuthorizeResponse> =
        myProfileApi.register(request)

    override fun login(request: LoginRequest): Call<AuthorizeResponse> =
        myProfileApi.login(request)

    override fun getProfile(): Call<ProfileResponse> =
        myProfileApi.getProfile()

    override fun editProfile(request: EditProfileRequest): Call<ProfileResponse> =
        myProfileApi.editProfile(request)

//    override fun getContacts(): Call<ContactsResponse> =
//        myProfileApi.getContacts()

    override suspend fun getContacts(): Flow<Response<ContactsResponse>> {
        val contacts: Flow<Response<ContactsResponse>> = flow {
            while (true) {
                val refreshContacts = myProfileApi.getContacts()
                emit(refreshContacts)
                kotlinx.coroutines.delay(2000)
            }
        }
        return contacts
    }

    override fun getAllUsers(): Call<UsersResponse> =
        myProfileApi.getAllUsers()

    override fun addContact(contactId: Int): Call<ContactsResponse> =
        myProfileApi.addContact(contactId)

    override fun deleteContact(contactId: Int): Call<ContactsResponse> =
        myProfileApi.deleteContact(contactId)

    override fun refreshToken(refreshToken: String): Call<RefreshTokenResponse> =
        myProfileApi.refreshToken(refreshToken)
}