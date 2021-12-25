package com.protsolo.data.remote

import com.protsolo.app.utils.Constants
import com.protsolo.data.remote.requests.EditProfileRequest
import com.protsolo.data.remote.requests.LoginRequest
import com.protsolo.data.remote.requests.RegisterUserRequest
import com.protsolo.data.remote.responses.*
import retrofit2.Call
import retrofit2.http.*

interface IMyProfileApi {

    @POST(Constants.POST_REGISTER_URL)
    fun register(@Body request: RegisterUserRequest): Call<AuthorizeResponse>

    @POST(Constants.POST_AUTHORIZE_URL)
    fun login(@Body request: LoginRequest): Call<AuthorizeResponse>

    @POST(Constants.POST_REFRESH_TOKEN_URL)
    fun refreshToken(@Header("Authorization") refreshToken: String) : Call<RefreshTokenResponse>

    @GET(Constants.GET_PROFILE_URL)
    fun getProfile(): Call<ProfileResponse>

    @POST(Constants.POST_EDIT_PROFILE_URL)
    fun editProfile(@Body request: EditProfileRequest) : Call<ProfileResponse>

    @GET(Constants.GET_ALL_USERS_URL)
    fun getAllUsers() : Call<UsersResponse>

    @FormUrlEncoded
    @POST(Constants.POST_ADD_CONTACT_URL)
    fun addContact(@Field("contactId") contactId: Int) : Call<ContactsResponse>

    @FormUrlEncoded
    @POST(Constants.POST_DELETE_CONTACT_URL)
    fun deleteContact(@Field("contactId") contactId: Int) : Call<ContactsResponse>

    @GET(Constants.GET_USER_CONTACTS_URL)
    fun getContacts() : Call<ContactsResponse>
}