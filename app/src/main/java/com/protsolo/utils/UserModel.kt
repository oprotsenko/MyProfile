package com.protsolo.utils

import android.widget.ImageView

class UserModel(image: ImageView? = null, name: String = "User Name", career: String = "User career", address: String = "User address" ) {
    var contactImage: ImageView? = image
    var contactName: String = name
    var contactCareer: String = career
    var contactAddress: String = address
}