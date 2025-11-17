package org.example.project.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.example.project.storage.AppDatabase
import org.example.project.storage.UserEntity
import org.example.project.storage.UserSession

class SignupViewModel(appDatabase: AppDatabase) : ViewModel() {
    var name by  mutableStateOf("")
    var password by  mutableStateOf("")

    var isError by  mutableStateOf(false)
    var errorMessage by  mutableStateOf("")

    val userDao = appDatabase.getUserDao()

    val users = MutableStateFlow<List<UserEntity>>(emptyList())

    fun getAllUsers() = viewModelScope.launch{
        userDao.getAllAsFlow().collect { list ->
            users.value = list
        }
    }
    fun resetInput() {
        name = ""
        password = ""
    }
    fun signup() : Boolean {
        isError = false
        errorMessage = ""

         if(!isBlankField()
             && !userExist()
             && !weakPassword()){
             viewModelScope.async (Dispatchers.IO) {
                 userDao.insertUser(name,password)
             }
                resetInput()
                return true
        } else {
            resetInput()
            isError = true
        }
        return false
    }
    fun userExist(): Boolean {
        var userExist = false

        return userExist
    }
    fun isBlankField(): Boolean {
        if(name.trim().isEmpty() || password.trim().isEmpty()) {
            isError = true
            errorMessage = "Empty Username or Password"
            return true
        }
        return false
    }

    fun weakPassword(): Boolean {
        val passwordChars = password.toCharArray().toList()
        val hasUpperCase = passwordChars.any { it.isUpperCase() }
        val hasLowerCase = passwordChars.any { it.isLowerCase() }
        val hasDigit = passwordChars.any { it.isDigit() }

        if(
            !hasUpperCase
            || !hasLowerCase
            || !hasDigit
            || password.length < 8) {
            errorMessage =
                "Weak Password! It must contain a capital letter, a small letter, a number and be at least 8 characters long"
            return true
        }
        return false
    }
}