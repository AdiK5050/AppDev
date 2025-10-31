package org.example.project

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AppViewModel : ViewModel() {
    var name by  mutableStateOf("")
    var password by  mutableStateOf("")

    fun updateUserName(name: String) {
        this.name = name
    }
    fun updatePassword(password: String) {
        this.password = password
    }
    fun checkUserInfo()  {
        if(name.equals("Adi") && password.equals("password")) {
            // Auth OK
        } else {
            // Auth not OK
        }
    }

    fun addUserInfo() {



    }
}