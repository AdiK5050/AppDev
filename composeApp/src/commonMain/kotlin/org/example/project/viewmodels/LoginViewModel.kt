package org.example.project.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.example.project.storage.UserDao
import org.example.project.storage.UserSession

class LoginViewModel(
    val userSession: UserSession,
    private val userDao: UserDao
) : ViewModel() {

    var name by mutableStateOf("")
    var password by mutableStateOf("")

    private var _loginSuccess = mutableStateOf(false)
    val loginSuccess: State<Boolean> = _loginSuccess

    private var _isError = mutableStateOf(false)
    val isError: State<Boolean> = _isError
    var errorMessage = mutableStateOf("")

    var isLoggedIn by mutableStateOf(userSession.isLoggedIn())

    fun resetInput() {
        name = ""
        password = ""
    }
    fun resetErrorStatus() {
        _isError.value = false
        errorMessage.value = ""
    }
   fun login(){
       viewModelScope.launch {
           if (!isBlankField() && userFound() && correctPassword()) {
               userSession.saveUserSession(name, password)
                   _loginSuccess.value = true
           }
           else {
               _isError.value = true
               resetInput()
           }
           println(errorMessage)
       }
   }

    private suspend fun userFound(): Boolean {
        val userFound = userDao.getByName(name)
        if (userFound != null) {
            return true
        }
        errorMessage.value = "User not found"
        return false
    }

    private suspend fun correctPassword(): Boolean {
        val correctPassword = userDao.getByName(name)?.password == password
        if (correctPassword) {
            isLoggedIn = true
            return true
        }
        errorMessage.value = "Incorrect Password"
        return false
    }

    fun isBlankField(): Boolean {
        if (name.trim().isEmpty() || password.trim().isEmpty()) {
            errorMessage.value = "Empty Username or Password"
            return true
        }
        return false
    }
}