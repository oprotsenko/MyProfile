package com.protsolo.data

import android.net.Uri
import com.protsolo.itemModel.UserModel

object ContactsDataFake {

    fun loadContacts(): MutableList<UserModel> {
        val list = mutableListOf<UserModel>()
        (0..15).forEach { i ->
            list.add(
                UserModel(
                    i.toLong(), getRandomImage(),
                    "$i name", "$i career", "$i address", "+3800000000"
                )
            )
        }
        return list
    }

    fun getRandomImage(): String {
        var random = (500..5000).random()
        while (Uri.parse("https://cspromogame.ru//storage/upload_images/avatars/$random.jpg") == null)
            random = (500..5000).random()
        return "https://cspromogame.ru//storage/upload_images/avatars/$random.jpg"
    }
}