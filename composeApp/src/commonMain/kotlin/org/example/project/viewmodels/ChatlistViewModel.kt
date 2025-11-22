package org.example.project.viewmodels

import androidx.lifecycle.ViewModel
import org.example.project.storage.AppDatabase
import org.example.project.storage.UserSession

class ChatListViewModel( appDatabase: AppDatabase, val userSession: UserSession): ViewModel() {
    val messageDao = appDatabase.getMessageDao()
}


