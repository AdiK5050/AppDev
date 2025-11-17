package org.example.project.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.example.project.storage.UserSession

class LoginViewModel(val userSession: UserSession) : ViewModel() {

    var name by  mutableStateOf("")
    var password by  mutableStateOf("")

    var isError by  mutableStateOf(false)
    var errorMessage by  mutableStateOf("")

    var isLoggedIn by mutableStateOf(userSession.isLoggedIn())
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
    fun userFound(): Boolean{
        var userFound = false
        viewModelScope.async {
            if(userSession.userDao.getByName(name) != null)
                userFound = true
        }
        if(userFound) {
            return true
        }
        errorMessage = "User not found"
        return false
    }

    fun correctPassword(): Boolean {
        var correctPassword = false
        viewModelScope.async {
            if(userSession.userDao.getByName(name)?.password == password)
                correctPassword = true
        }
        if(correctPassword) {
            isLoggedIn = true
            return true
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