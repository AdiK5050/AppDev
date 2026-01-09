package com.adi_chat.app.ui.app.contact.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adi_chat.app.storage.AppDatabase
import com.adi_chat.app.storage.ChannelEntity
import com.adi_chat.app.storage.ChannelMembers
import com.adi_chat.app.shared.viewmodels.SharedViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ContactsViewModel(appDatabase: AppDatabase, private val sharedViewModel: SharedViewModel) : ViewModel() {

    var _loading = mutableStateOf(false)
    val loading = _loading.value
    private val userDao = appDatabase.getUserDao()
    private val channelDao = appDatabase.getChannelDao()
    private var _create = mutableStateOf(false)
    val create = _create

    private var _channelMemberIDs = mutableListOf<Int>()
    val users = userDao.getAllAsFlow().
            stateIn(
                viewModelScope,
                SharingStarted.Companion.WhileSubscribed(1000),
                emptyList()
            )

    fun createChannel(channelName: String) {
        if (_channelMemberIDs.isNotEmpty()) {
            _loading.value = true
            viewModelScope.launch(Dispatchers.IO) {
                channelDao.insertChannel(
                    ChannelEntity(
                        channelName = channelName,
                        userCreatedID = sharedViewModel.LOGGED_IN_USER_ID.intValue,
                        numberOfMembers = _channelMemberIDs.size
                    )
                )
                val channelID = channelDao.getChannelIDByCandidateKey(
                    channelName,
                    sharedViewModel.LOGGED_IN_USER_ID.intValue
                )
                sharedViewModel.currentChannelID.intValue = channelID
                _loading.value = false
                for (member in _channelMemberIDs) {
                    channelDao.insertChannelMember(
                        ChannelMembers(
                            channelID = channelID,
                            memberID = member
                        )
                    )
                }
            }
        }
    }
    fun showCreateButton() {
        _create.value = true
    }
    fun hideCreateButton() {
        _create.value = false
    }
    fun addMember(member: Int) {
        _channelMemberIDs.add(member)
        if(_channelMemberIDs.isNotEmpty()) showCreateButton()
    }
    fun removeMember(member: Int) {
        _channelMemberIDs.remove(member)
        if(!_channelMemberIDs.isNotEmpty()) hideCreateButton()
    }
}