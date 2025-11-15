package org.example.project.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class MessageViewModel(val database: Database) : ViewModel() {

     val messageHistory = mutableStateListOf<Message>()
     val profilePic = mutableStateOf(database.getProfilePic())

     val value = mutableStateOf("")

    fun initMessageHistory() {
        messageHistory.addAll(database.messageHistory)
        for(message in messageHistory) {
            message.profilePic = profilePic.value
        }
    }
    fun addMessage(message: String) {
        val message = Message("Aditya", message.trim(),null, "receiver")
        messageHistory.add(message)
    }
}