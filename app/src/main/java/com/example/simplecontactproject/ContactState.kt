package com.example.simplecontactproject

data class ContactState(
    val contacts :List<Contact> = emptyList(),
    val firstName:String = "",
    val lastName:String = "",
    val phoneNumber:String = "",
    val emailAddress:String = "",
    val idAddingContact : Boolean = false,
    val sortType: SortType = SortType.FIRST_NAME
    )
