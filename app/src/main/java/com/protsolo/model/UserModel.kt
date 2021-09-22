package com.protsolo.model

class UserModel(image: String = "https://icon-library.com/images/person-icon-png-transparent/person-icon-png-transparent-15.jpg", name: String = "User Name", career: String = "User career", address: String = "User address" ) {
    var contactImage: String = image
    var contactName: String = name
    var contactCareer: String = career
    var contactAddress: String = address
}