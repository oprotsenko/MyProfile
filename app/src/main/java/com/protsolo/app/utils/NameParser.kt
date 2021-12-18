package com.protsolo.app.utils

class NameParser {
    fun parseName(email: String?): String {
        val name = email?.substring(0, email.indexOf('@'))
        return if (name?.contains('.', false) == true) {
            val index = name.indexOf('.') + 1
            name.replace('.', ' ').replaceFirst(name[0], name[0].uppercaseChar())
                .replace(name[index], name[index].uppercaseChar(), true)
        } else {
            name?.replaceFirst(name[0], name[0].uppercaseChar()).toString()
        }
    }
}