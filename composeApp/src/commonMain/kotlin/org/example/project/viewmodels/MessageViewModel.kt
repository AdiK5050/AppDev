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
import org.example.project.storage.ChannelEntity
import org.example.project.storage.MessageEntity
import org.example.project.storage.UserSession

data class ChannelMembersInfo(
    val userID: Int,
    val username: String,
    val profilePic: ByteArray?
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ChannelMembersInfo

        if (userID != other.userID) return false
        if (username != other.username) return false
        if (!profilePic.contentEquals(other.profilePic)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = userID
        result = 31 * result + username.hashCode()
        result = 31 * result + (profilePic?.contentHashCode() ?: 0)
        return result
    }
}

class MessageViewModel(appDatabase: AppDatabase, val userSession: UserSession, val sharedViewModel: SharedViewModel) : ViewModel() {

    var _loading: MutableState<Boolean> = mutableStateOf(sharedViewModel.isLoading.value)
    val loading = _loading.value
    val messageDao = appDatabase.getMessageDao()
    val channelDao = appDatabase.getChannelDao()

    val senderID = mutableIntStateOf(sharedViewModel.LOGGED_IN_USER_ID.intValue)
    var channelID = mutableIntStateOf(sharedViewModel.currentChannelID.intValue)
    var channel = channelDao.getChannelByChannelID(channelID.intValue)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ChannelEntity(0,"",0,0)
        )
    val channelMembers = channelDao.getMembersByChannelID(sharedViewModel.currentChannelID.intValue)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    val messageHistory = messageDao.getAllMessagesByChannelID(sharedViewModel.currentChannelID.intValue)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    fun startLoading() {
        sharedViewModel.showLoader()
    }
    fun stopLoading() {
        sharedViewModel.hideLoader()
    }
    fun setCurrentUserID(userID: Int) {
        sharedViewModel.currentUserID.intValue = userID
    }
    fun addMessage(message: String) {
        channelID.intValue = sharedViewModel.currentChannelID.intValue
        viewModelScope.launch(Dispatchers.IO) {
            messageDao.insertMessage(MessageEntity(channelID = channelID.intValue, senderID = senderID.intValue, message = message))
        }
    }
}