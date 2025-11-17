package org.example.project.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.example.project.storage.AppDatabase
import org.example.project.storage.UserEntity
import org.example.project.storage.UserSession

class LoginViewModel(val userSession: UserSession, appDatabase: AppDatabase) : ViewModel() {

    var name by  mutableStateOf("")
    var password by  mutableStateOf("")

    var isError by  mutableStateOf(false)
    var errorMessage by  mutableStateOf("")

    val userDao = appDatabase.getUserDao()

    var users = MutableStateFlow<List<UserEntity>>(emptyList())
    var isLoggedIn by mutableStateOf(userSession.isLoggedIn())

    fun getAllUsers() = viewModelScope.launch {
        userDao.getAllAsFlow().collect { list ->
            users.value = list
        }
    }
    fun resetInput() {
        name = ""
        password = ""
    }
    fun login() : Boolean {
        isError = false
        errorMessage = ""
        if (!isBlankField() && userFound() && correctPassword()) {
            userSession.saveUserSession(name, password)
            resetInput()
            return true
        }
        resetInput()
        isError = true
        return false
    }
    fun userFound(): Boolean{
        users.value.forEach { user ->
            if(user.username == name)
                return true
        }
        errorMessage = "User not found"
        return false
    }

    fun correctPassword(): Boolean {
        users.value.forEach { user ->
            if(user.username == name && user.password == password)
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