package com.adi_chat.app.shared.viewmodels

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.adi_chat.app.storage.UserSession

class SharedViewModel(userSession: UserSession): ViewModel() {
    val LOGGED_IN_USER_ID = mutableIntStateOf(userSession.getUserId())

    var currentChannelID = mutableIntStateOf(0)

    var currentUserID = mutableIntStateOf(0)

    val isLoading = mutableStateOf(false)

    fun showLoader() {
        isLoading.value = true
    }

    fun hideLoader() {
        isLoading.value = false
    }

}