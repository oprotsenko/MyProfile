package com.protsolo.utils

import com.protsolo.model.UserModel

object ContactsData {

    fun loadContacts(): List<UserModel> {
        val list = mutableListOf<UserModel>()
        (0..4).forEach { i -> list.add(UserModel("", "$i name", "$i career", "$i address")) }
        return list
    }

//        fun loadContacts():List<UserModel> {
//        val list = mutableListOf<UserModel>()
//        Log.d("ALARM", "path.toString()")
////        val file = File("C:\\Users\\miste\\AndroidStudioProjects\\MyProfile\\app\\src\\main\\res\\assets\\contacts.csv")
//        val file = "Name,Given Name,Additional Name,Family Name,Yomi Name,Given Name Yomi,Additional Name Yomi,Family Name Yomi,Name Prefix,Name Suffix,Initials,Nickname,Short Name,Maiden Name,Birthday,Gender,Location,Billing Information,Directory Server,Mileage,Occupation,Hobby,Sensitivity,Priority,Subject,Notes,Language,Photo,Group Membership,Phone 1 - Type,Phone 1 - Value,Phone 2 - Type,Phone 2 - Value,Address 1 - Type,Address 1 - Formatted,Address 1 - Street,Address 1 - City,Address 1 - PO Box,Address 1 - Region,Address 1 - Postal Code,Address 1 - Country,Address 1 - Extended Address,Organization 1 - Type,Organization 1 - Name,Organization 1 - Yomi Name,Organization 1 - Title,Organization 1 - Department,Organization 1 - Symbol,Organization 1 - Location,Organization 1 - Job Description,Website 1 - Type,Website 1 - Value"
////        val rows: List<List<String>> = csvReader().readAll(file)
////        Log.d("ALARM", rows[0][0])
////        var j = 0
////        (rows).forEach { i -> list.add(UserModel("", rows[j++][0], "career", "address")) }
//        csvReader().open(file) {
//            readAllAsSequence().forEach { row: List<String> ->
//                Log.d("ALARM", "row[0]")
//                list.add(UserModel("", row[0], "career", "address"))
//            }
//        }
//        return list
//    }
}