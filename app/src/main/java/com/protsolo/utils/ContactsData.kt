package com.protsolo.utils

import com.protsolo.model.UserModel

object ContactsData {

    fun loadContacts(): MutableList<UserModel> {
        val list = mutableListOf<UserModel>()
        (0..4).forEach { i -> list.add(
            UserModel("https://scontent.fiev17-2.fna.fbcdn.net/v/t1.6435-9/60336113_2169614983075023_4549265995994234880_n.jpg?_nc_cat=110&ccb=1-5&_nc_sid=e3f864&_nc_ohc=iX7fG3rbI2QAX8dAVdp&_nc_oc=AQl6JBi7KU-x66EVpQynuxMYVEF7X_ELwgyLLhhRHDHsg_ZZkt5kzZ5LAF_cH3gpTio&_nc_ht=scontent.fiev17-2.fna&oh=2f7de9659dfc36eb2a0b50181bc3030e&oe=61746942",
                "$i name", "$i career", "$i address")) }
        return list
    }

//    fun loadContacts(): UserModel =
//        UserModel("https://icon-library.com/images/person-icon-png-transparent/person-icon-png-transparent-15.jpg",
//            "name",
//            "career",
//            "address")












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