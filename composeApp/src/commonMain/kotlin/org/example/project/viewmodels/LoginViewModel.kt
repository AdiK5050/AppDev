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
        if (!isBlankField() && userFound() && correctPassword()) {
            resetInput()
            return true
        }

        resetInput()
        isError = true
        return false
    }
    fun userFound() :Boolean{
        for(user in database.users) {
            if (user.name == name) {
                return true
            }
        }
        errorMessage = "User not found"
        return false
    }

    fun correctPassword(): Boolean {
        for (user in database.users) {
            if (user.name == name && user.password == password) {
                return true
            }
        }
        errorMessage = "Incorrect Password"
        return false
    }
    fun isBlankField(): Boolean {
        if(name.trim().isEmpty() || password.trim().isEmpty()) {
            isError = true
            errorMessage = "Empty Username or Password"
            return true
        }
        return false
    }
}