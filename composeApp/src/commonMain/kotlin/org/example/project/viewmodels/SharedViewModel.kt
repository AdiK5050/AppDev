package org.example.project.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.example.project.storage.AppDatabase
import org.example.project.storage.UserEntity
import org.example.project.storage.UserSession
import kotlin.properties.Delegates

class SharedViewModel(userSession: UserSession, appDatabase: AppDatabase): ViewModel() {

    val userDao = appDatabase.getUserDao()

    var LOGGED_IN_USER_ID =  mutableIntStateOf(0)

    init {
        viewModelScope.launch {
            LOGGED_IN_USER_ID.intValue = userDao.getUIDByName(userSession.getUsername())
        }
    }
}