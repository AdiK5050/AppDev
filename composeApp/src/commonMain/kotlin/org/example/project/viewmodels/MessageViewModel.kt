package org.example.project.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.launch
import org.example.project.storage.AppDatabase
import org.example.project.storage.MessageEntity
import org.example.project.storage.UserSession

class MessageViewModel(appDatabase: AppDatabase, val userSession: UserSession) : ViewModel() {

    val messageDao = appDatabase.getMessageDao()
     val messageHistory = mutableStateOf(messageDao.getAllAsFlow())
     val uidFrom = mutableStateOf(0)
     val uidTo : MutableState<Int?> = mutableStateOf(null)

    fun addMessage(message: String) {
        viewModelScope.launch(Dispatchers.IO) {
            messageDao.insert(uidTo.value,uidFrom.value,message)
            if(uidTo.value == null) println("uidTo was null")
            else println("uidTo was not null")
        }
    }
}