package com.adi_chat.app.ui.app.login.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adi_chat.app.storage.UserDao
import com.adi_chat.app.storage.UserSession
import com.adi_chat.app.shared.viewmodels.SharedViewModel
import kotlinx.coroutines.launch

class LoginViewModel(
    val userSession: UserSession,
    val sharedViewModel: SharedViewModel,
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
               userSession.saveUserSession(name, password, userDao.getUIDByName(name))
               sharedViewModel.LOGGED_IN_USER_ID.intValue = userSession.getUserId()
                   _loginSuccess.value = true
           }
           else {
               _isError.value = true
               resetInput()
           }
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