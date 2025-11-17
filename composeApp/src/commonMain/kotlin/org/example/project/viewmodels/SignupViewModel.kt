package org.example.project.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.example.project.storage.UserDao
import org.example.project.storage.UserEntity

class SignupViewModel(
    private val userDao: UserDao
) : ViewModel() {
    var name by mutableStateOf("")
    var password by mutableStateOf("")

    var isError by mutableStateOf(false)
    var errorMessage by mutableStateOf("")

    fun resetInput() {
        name = ""
        password = ""
    }

    fun signup() {
        isError = false
        errorMessage = ""

        viewModelScope.launch(Dispatchers.IO) {
            if (!isBlankField() && !userExist() && !weakPassword()) {
                userDao.insertUser(UserEntity(username = name, password = password))
                resetInput()
            } else {
                resetInput()
                isError = true
            }
        }

    }

    private suspend fun userExist(): Boolean {
        return userDao.getByName(name) != null
    }

    fun isBlankField(): Boolean {
        if (name.trim().isEmpty() || password.trim().isEmpty()) {
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

        if (
            !hasUpperCase
            || !hasLowerCase
            || !hasDigit
            || password.length < 8
        ) {
            errorMessage =
                "Weak Password! It must contain a capital letter, a small letter, a number and be at least 8 characters long"
            return true
        }
        return false
    }
}