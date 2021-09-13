package com.protsolo.utils

import android.widget.ImageView
import android.widget.TextView

class UserModel(image: ImageView?, name: String, career: String, address: String ) {
    var contactImage: ImageView? = image
    var contactName: String = name
    var contactCareer: String = career
    var contactAddress: String = address
}