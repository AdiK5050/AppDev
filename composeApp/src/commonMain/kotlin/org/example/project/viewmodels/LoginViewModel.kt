package org.example.project.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class LoginViewModel(val database: Database) : ViewModel() {

    var name by  mutableStateOf("")
    var password by  mutableStateOf("")

    var isError by  mutableStateOf(false)
    var errorMessage by  mutableStateOf("")
    fun resetInput() {
        name = ""
        password = ""
    }

    fun login() : Boolean {
        isError = false
        errorMessage = ""
        for(user in database.users) {
            if (user.name == name && user.password == password) {
                resetInput()
                return true
            }
        }
        resetInput()
        isError = true
        errorMessage = "Log in failed"
        return false
    }
}