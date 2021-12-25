package com.protsolo.data.remote.responses

import com.protsolo.app.item.UserModel

data class ContactsResponse(
    val status: String,
    val code: Int,
    val message: String?,
    val data: ContactsList
)

data class ContactsList(
    val contacts: List<UserModel>
)