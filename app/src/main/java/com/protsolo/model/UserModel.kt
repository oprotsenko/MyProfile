package com.protsolo.model

import com.protsolo.utils.Constants

class UserModel(
    var image: String = Constants.DEFAULT_IMAGE,
    var name: String = "User Name",
    var career: String = "User career",
    var address: String = "User address"
) {
}