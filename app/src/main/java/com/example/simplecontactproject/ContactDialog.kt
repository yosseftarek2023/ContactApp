package com.example.simplecontactproject

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ContactDialog(
    state: ContactState,
    clickEvent: (ContactEvent) -> Unit,
){
    AlertDialog(
        modifier = Modifier,
        onDismissRequest = {
            clickEvent(ContactEvent.HideDialog)
        },
        title = { Text(text = "Add New Contact") },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                TextField(
                    value = state.firstName,
                    onValueChange = {
                        clickEvent(ContactEvent.AddFirstName(it))
                    },
                    placeholder = {
                        Text(text = "FirstName")
                    }
                )
                TextField(
                    value = state.lastName,
                    onValueChange = {
                        clickEvent(ContactEvent.AddLastName(it))
                    },
                    placeholder = {
                        Text(text = "LastName")
                    }
                )
                TextField(
                    value = state.emailAddress,
                    onValueChange = {
                        clickEvent(ContactEvent.AddEmail(it))
                    },
                    placeholder = {
                        Text(text = "Email")
                    }
                )
                TextField(
                    value = state.phoneNumber,
                    onValueChange = {
                        clickEvent(ContactEvent.AddPhoneNumber(it))
                    },
                    placeholder = {
                        Text(text = "PhoneNumber")
                    }
                )
            }
        },
        confirmButton = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Button(onClick = {
                    clickEvent(ContactEvent.InsertContact)
                }) {
                    Text(text = "Add Contact")
                }
            }
        }
    )
}
