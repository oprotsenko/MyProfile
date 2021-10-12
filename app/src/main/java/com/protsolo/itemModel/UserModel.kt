package com.protsolo.itemModel

import com.protsolo.utils.Constants

data class UserModel(
    var userId: Long = 0,
    var image: String = Constants.DEFAULT_IMAGE,
    var name: String = "User Name",
    var career: String = "User career",
    var address: String = "User address"
)