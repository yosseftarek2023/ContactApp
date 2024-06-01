package com.example.simplecontactproject

sealed interface ContactEvent {
    data object InsertContact: ContactEvent
    data class DeleteContact(val contact: Contact):ContactEvent
    data class AddFirstName(val firstName:String):ContactEvent
    data class AddLastName(val lastName:String):ContactEvent
    data class AddPhoneNumber(val phoneNumber:String):ContactEvent
    data class AddEmail(val emailAddress:String):ContactEvent
    data object ShowDialog:ContactEvent
    data object HideDialog:ContactEvent
    data class SortContacts(val sortType: SortType):ContactEvent
}