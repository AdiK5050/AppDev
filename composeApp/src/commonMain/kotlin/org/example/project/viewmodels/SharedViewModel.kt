package org.example.project.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import org.example.project.storage.UserSession

class SharedViewModel(userSession: UserSession): ViewModel() {
    val LOGGED_IN_USER_ID = mutableIntStateOf(userSession.getUserId())

    var currentChannelID by mutableStateOf(0)

    var currentUserID by mutableIntStateOf(0)

}