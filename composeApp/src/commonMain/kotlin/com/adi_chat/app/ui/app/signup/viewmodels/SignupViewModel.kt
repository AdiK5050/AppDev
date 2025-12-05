package com.adi_chat.app.ui.app.signup.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adi_chat.app.storage.UserDao
import com.adi_chat.app.storage.UserEntity
import kotlinx.coroutines.launch

class SignupViewModel(
    private val userDao: UserDao
) : ViewModel() {
    var name by mutableStateOf("")
    var password by mutableStateOf("")

    var _signupSuccessful = mutableStateOf(false)
    val signupSuccessful: State<Boolean> = _signupSuccessful
    var _isError = mutableStateOf(false)
    val isError: State<Boolean> = _isError
    var _errorMessage = mutableStateOf("")
    val errorMessage: State<String> = _errorMessage


    fun resetInput() {
        name = ""
        password = ""
    }

    fun resetErrorStatus() {
        _isError.value = false
        _errorMessage.value = ""
    }

    fun signup() {
        viewModelScope.launch {
            if(!isBlankField() && !weakPassword() && !userAlreadyExist()) {
                addUser(name, password)
                _signupSuccessful.value = true
            }
            else {
                _isError.value = true
                resetInput()
            }
        }
    }

    private suspend fun addUser(name: String, password: String) {
        userDao.insertUser(UserEntity(username = name, password = password))
    }

    private suspend fun userAlreadyExist(): Boolean {
        val user = userDao.getByName(name)
        if(user != null){
            _errorMessage.value = "User already exists."
            return true
        }
        return false
    }

    fun isBlankField(): Boolean {
        if (name.trim().isEmpty() || password.trim().isEmpty()) {
            _errorMessage.value = "Empty Username or Password"
            return true
        }
        return false
    }

    fun weakPassword(): Boolean {
        val passwordChars = password.toCharArray().toList()
        val hasUpperCase = passwordChars.any { it.isUpperCase() }
        val hasLowerCase = passwordChars.any { it.isLowerCase() }
        val hasDigit = passwordChars.any { it.isDigit() }

        if (
            !hasUpperCase
            || !hasLowerCase
            || !hasDigit
            || password.length < 8
        ) {
            _errorMessage.value =
                "Weak Password! It must contain a capital letter, a small letter, a number and be at least 8 characters long"
            return true
        }
        return false
    }
}