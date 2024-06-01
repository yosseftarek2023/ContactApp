package com.example.simplecontactproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.simplecontactproject.ui.theme.SimpleContactProjectTheme



@Suppress("UNCHECKED_CAST")
class MainActivity : ComponentActivity() {

    private val database by lazy {
        Room.databaseBuilder(
            applicationContext,
            ContactDatabase::class.java,
            "Contact Database",
        ).build()
    }
    private val viewModel by viewModels<ContactViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory{
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return ContactViewModel(dao = database.dao) as T
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SimpleContactProjectTheme {
                val state by viewModel.publicState.collectAsState()
                ContactView(state = state , clickEvent = viewModel::clickEvent )
            }
        }
    }
}

