package com.example.simplecontactproject

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ContactViewModel(
    private val dao: ContactDao
):ViewModel() {

    private val sortType = MutableStateFlow(SortType.FIRST_NAME)
    private val state = MutableStateFlow(ContactState())

    @OptIn(ExperimentalCoroutinesApi::class)
    private val contacts = sortType.flatMapLatest {
        sortType ->
        when(sortType) {
            SortType.FIRST_NAME -> {
                dao.getContactOrderedByFirstName()
            }

            SortType.LAST_NAME -> {
                dao.getContactOrderedBylastName()
            }

            SortType.EMAIL -> {
                dao.getContactOrderedByemail()
            }

            SortType.PHONE_NUMBER -> {
                dao.getContactOrderedByNumber()
            }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    val publicState = kotlinx.coroutines.flow.combine(state,sortType,contacts){
            state,sortType,contacts ->
        state.copy(
            contacts = contacts,
            sortType = sortType
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000),ContactState())


    fun clickEvent(event: ContactEvent){
        when(event){
            is ContactEvent.AddEmail -> {
                state.update { it.copy(emailAddress = event.emailAddress) }
            }
            is ContactEvent.AddFirstName -> {
                state.update { it.copy(firstName = event.firstName) }
            }
            is ContactEvent.AddLastName -> {
                state.update { it.copy(lastName = event.lastName) }
            }
            is ContactEvent.AddPhoneNumber -> {
                state.update { it.copy(phoneNumber = event.phoneNumber) }
            }
            is ContactEvent.DeleteContact -> {
                viewModelScope.launch {
                    dao.deleteContact(event.contact)
                }
            }
            ContactEvent.HideDialog -> {
                state.update {it.copy(idAddingContact = false)}
            }
            ContactEvent.InsertContact -> {
                val firstName = state.value.firstName
                val lastName = state.value.lastName
                val phoneNumber = state.value.phoneNumber
                val email = state.value.emailAddress

                if(firstName.isBlank() || lastName.isBlank() || phoneNumber.isBlank() || email.isBlank()){
                    return
                }

                val contact = Contact(
                    firstName = firstName,
                    lastName = lastName,
                    emailAddress = email,
                    phoneNumber = phoneNumber
                )

                viewModelScope.launch {
                    dao.upsertContact(contact)
                }
                state.update {
                    it.copy(
                        firstName ="",
                        lastName = "",
                        phoneNumber = "",
                        emailAddress = "",
                        idAddingContact = false
                    )
                }
            }
            ContactEvent.ShowDialog -> {
                state.update {it.copy(idAddingContact = true)}
            }
            is ContactEvent.SortContacts -> {
                sortType.value = event.sortType
            }
        }
    }

}