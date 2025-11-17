package org.example.project.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.example.project.storage.Database
import org.example.project.storage.MessageEntity
import org.example.project.storage.UserSession

class MessageViewModel(val database: Database, val userSession: UserSession) : ViewModel() {

     val messageHistory = MutableStateFlow<List<MessageEntity>>(emptyList())
     val uidFrom = mutableStateOf(0)
     val uidTo : MutableState<Int?> = mutableStateOf(null)

    fun updateUidToProfile (uid: Int) {
        database.uidToProfilePage.value = uid
    }
    fun init() = viewModelScope.launch(Dispatchers.Main) {
        initUidFrom()
        database.messageEntity.value.forEach {
            messageEntity -> messageHistory.value += messageEntity
        }
    }
    fun initUidFrom() {
        database.userEntity.value.forEach {  userEntity ->
            if (userEntity.username == userSession.getUsername()) {
                uidFrom.value = userEntity.uid
            } else {
                println("No user found in messageViewModel.getUidFrom()")
            }
        }
    }
    fun getUsernameFromUid(uid: Int) : String {
        database.userEntity.value.forEach {
            userEntity ->
            if (userEntity.uid == uid) {
                return userEntity.username
            }
        }
        return ""
    }
    fun getUidTo() {

    }
    fun addMessage(message: String) {
        val messageEntity = MessageEntity(uidTo = uidTo.value, uidFrom =uidFrom.value, message = message)
        messageHistory.value += messageEntity
        viewModelScope.launch(Dispatchers.Main) {
            database.messageDao.insert(uidTo.value,uidFrom.value,message)
            if(uidTo.value == null) println("uidTo was null")
            else println("uidTo was not null")
        }
    }
}