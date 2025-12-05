package com.adi_chat.app.ui.app.chat_list.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adi_chat.app.storage.AppDatabase
import com.adi_chat.app.storage.UserSession
import com.adi_chat.app.shared_viewmodels.SharedViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.collections.emptyList

data class ChannelInfo(
    val channelID: Int,
    val channelName: String,
    val lastMessage: String?,
    val lastMessageTime: Long?,
)

class ChatListViewModel(appDatabase: AppDatabase,
                        private val userSession: UserSession,
                        private val sharedViewModel: SharedViewModel
)
    : ViewModel() {
    var _loading = mutableStateOf(false)
    val loading = _loading.value
    val userDao = appDatabase.getUserDao()
    val channelDao = appDatabase.getChannelDao()

    var profilePic: MutableState<ByteArray?> = mutableStateOf(ByteArray(0))

    fun initAll() {
        viewModelScope.launch {
            profilePic.value = userDao.getPicByUID(sharedViewModel.LOGGED_IN_USER_ID.intValue)
        }
    }
    val channelInfo = channelDao.getChannelInfoByMemberID(sharedViewModel.LOGGED_IN_USER_ID.intValue).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(1000),
        initialValue = emptyList()
    )
    fun setCurrentChannel(channelID: Int) {
        sharedViewModel.currentChannelID.intValue = channelID
    }
    fun clearUserSession() {
        userSession.clearUserSession()
    }
}
