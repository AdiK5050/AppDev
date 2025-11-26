package org.example.project.viewmodels

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

class MessageViewModel(appDatabase: AppDatabase, val userSession: UserSession, val sharedViewModel: SharedViewModel) : ViewModel() {

    var _loading = mutableStateOf(false)
    val loading = _loading.value
    val userDao = appDatabase.getUserDao()
    val messageDao = appDatabase.getMessageDao()
    val channelDao = appDatabase.getChannelDao()

    val senderID = mutableIntStateOf(sharedViewModel.LOGGED_IN_USER_ID.intValue)
    var channelID = mutableIntStateOf(0)
    var channelName = mutableStateOf("")
    val channelMembers: MutableList<UserEntity> = mutableListOf()
    val messageHistory = messageDao.getAllMessagesByChannelID(sharedViewModel.currentChannelID)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    fun initAll() {
        viewModelScope.launch {
            channelID.intValue = sharedViewModel.currentChannelID
            channelName.value = channelDao.getChannelNameByChannelID(channelID.intValue)
            val members = channelDao.getMembersByChannelID(channelID.intValue)
            members.forEach { memberID ->
            channelMembers.add(userDao.getUserByUserID(memberID))
            }
        }
    }

    fun setCurrentUserID(userID: Int) {
        sharedViewModel.currentUserID = userID
    }
    fun addMessage(message: String) {
        channelID.intValue = sharedViewModel.currentChannelID
        viewModelScope.launch(Dispatchers.IO) {
            messageDao.insertMessage(MessageEntity(channelID = channelID.intValue, senderID = senderID.intValue, message = message))
        }
    }
}