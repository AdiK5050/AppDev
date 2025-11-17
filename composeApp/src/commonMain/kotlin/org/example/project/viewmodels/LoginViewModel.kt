package org.example.project.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.example.project.storage.UserSession

class LoginViewModel(val userSession: UserSession) : ViewModel() {

    var name by mutableStateOf("")
    var password by mutableStateOf("")

    var isError by mutableStateOf(false)
    var errorMessage by mutableStateOf("")

    var isLoggedIn by mutableStateOf(userSession.isLoggedIn())
    fun resetInput() {
        name = ""
        password = ""
    }

    fun login() {
        isError = false
        errorMessage = ""
        viewModelScope.launch {
            if (!isBlankField() && userFound() && correctPassword()) {
                resetInput()
                return@launch
            }

            resetInput()
            isError = true
        }
    }

    private suspend fun userFound(): Boolean {
        val userFound = userSession.userDao.getByName(name) != null
        if (userFound) {
            return true
        }
        errorMessage = "User not found"
        return false
    }

    private suspend fun correctPassword(): Boolean {
        val correctPassword = userSession.userDao.getByName(name)?.password == password
        if (correctPassword) {
            isLoggedIn = true
            return true
        }
        errorMessage = "Incorrect Password"
        return false
    }

    fun isBlankField(): Boolean {
        if (name.trim().isEmpty() || password.trim().isEmpty()) {
            isError = true
            errorMessage = "Empty Username or Password"
            return true
        }
        return false
    }
}