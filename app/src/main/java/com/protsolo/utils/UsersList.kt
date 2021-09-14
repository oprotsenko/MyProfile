package com.protsolo.utils

class UsersList(var usersList: MutableList<UserModel> = getUsersList())

fun getUsersList(): MutableList<UserModel> {
    val olena = UserModel(null, "Olena", "student", "Kyiv")
    val evgen = UserModel(null, "Evgen", "student", "Kyiv")
    val leo = UserModel(null, "Leo", "child", "Kyiv")
    return mutableListOf(olena, evgen, leo)
}
