package com.protsolo.data

import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.ContactsContract
import com.protsolo.app.item.UserModel
import com.protsolo.app.utils.Constants
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object ContactsDataFake : KoinComponent {

    private val context: Context by inject()

    fun loadContacts(): MutableList<UserModel> {
        val list = mutableListOf<UserModel>()
        if (!Constants.PHONEBOOK_CONTACTS) {
            (0..15).forEach { i ->
                list.add(
                    UserModel(
                        i.toLong(), getRandomImage(),
                        "$i name", "$i career", "$i address", "+3800000000",""
                    )
                )
            }
        } else {
            val cr: ContentResolver = context.contentResolver
            val cur: Cursor? = cr.query(
                ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null
            )
            if ((cur?.count ?: 0) > 0) {
                while (cur != null && cur.moveToNext()) {
                    val id: String = cur.getString(
                        cur.getColumnIndexOrThrow(ContactsContract.Contacts._ID)
                    )
                    val name: String = cur.getString(
                        cur.getColumnIndexOrThrow(
                            ContactsContract.Contacts.DISPLAY_NAME
                        )
                    )
                    if (cur.getInt(
                            cur.getColumnIndexOrThrow(
                                ContactsContract.Contacts.HAS_PHONE_NUMBER
                            )
                        ) > 0
                    ) {
                        val pCur: Cursor? = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            arrayOf(id),
                            null
                        )
                        while (pCur?.moveToNext() == true) {
                            val phoneNo: String = pCur.getString(
                                pCur.getColumnIndexOrThrow(
                                    ContactsContract.CommonDataKinds.Phone.NUMBER
                                )
                            )
                            val user = UserModel(
                                id.toLong(),
                                getRandomImage(),
                                name,
                                "", "", phoneNo, ""
                            )
                            list.add(user)
                        }
                        pCur?.close()
                    }
                }
            }
            cur?.close()
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