package org.example.project.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.example.project.storage.AppDatabase
import org.example.project.storage.MessageEntity
import org.example.project.storage.UserEntity
import org.example.project.storage.UserSession

class MessageViewModel(appDatabase: AppDatabase, val userSession: UserSession) : ViewModel() {

    val messageDao = appDatabase.getMessageDao()
    val userDao = appDatabase.getUserDao()

    val messageHistory = messageDao.getAllAsFlow()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val uidFrom = mutableIntStateOf(0)
    val uidTo: MutableState<Int?> = mutableStateOf(null)

    fun getUserEntity(uid: Int) : UserEntity? {
        var userEntity: UserEntity? = null
        viewModelScope.launch(Dispatchers.IO) {
            userEntity = userDao.getByUID(uid)
        }
        return userEntity
    }
    suspend fun getUidFrom() {
        val userEntity = userDao.getByName(userSession.getUsername())
        if(userEntity != null) {
            uidFrom.intValue = userEntity.uid
        }
    }
    fun addMessage(message: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getUidFrom()
            messageDao.insertMessage(MessageEntity(uidTo = uidTo.value, uidFrom = uidFrom.intValue, message = message))
            if (uidTo.value == null) println("uidTo was null")
            else println("uidTo was not null")
        }
    }
}