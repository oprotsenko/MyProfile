package com.protsolo.database

import android.net.Uri
import com.protsolo.itemModel.UserModel

object ContactsDataFake {

    fun loadContacts(): MutableList<UserModel> {
        val list = mutableListOf<UserModel>()
        (0..15).forEach { i ->
            var random = (500..5000).random()
            while (Uri.parse("https://cspromogame.ru//storage/upload_images/avatars/$random.jpg") == null)
                random = (500..5000).random()
            list.add(
                UserModel(
                    i.toLong(), "https://cspromogame.ru//storage/upload_images/avatars/$random.jpg",
                    "$i name", "$i career", "$i address"
                )
            )
        }
        return list
    }
}