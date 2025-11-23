package org.example.project.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.example.project.storage.AppDatabase
import org.example.project.storage.UserSession

class ChatListViewModel(appDatabase: AppDatabase, val userSession: UserSession, val sharedViewModel: SharedViewModel): ViewModel() {

    var profilePic: MutableState<ByteArray?>
    val userDao = appDatabase.getUserDao()
    val messageDao = appDatabase.getMessageDao()
    val channelDao = appDatabase.getChannelDao()
    val channels = channelDao.getAllAsFlow()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    fun getLastMessage (channelID: Int) : String? {
        var lastMessage: String? = null
        viewModelScope.launch {
            lastMessage = messageDao.getLastMessageByChannelID(channelID = channelID)
        }
        return lastMessage
    }
    fun initProfilePic() {
        viewModelScope.launch {
            profilePic.value = userDao.getPicByUID(sharedViewModel.LOGGED_IN_USER_ID)
        }
    }
}
