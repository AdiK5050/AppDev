package org.example.project.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import org.example.project.storage.UserSession
import org.example.project.storage.User

class SignupViewModel(val userSession: UserSession) : ViewModel() {
    var name by  mutableStateOf("")
    var password by  mutableStateOf("")

    var isError by  mutableStateOf(false)
    var errorMessage by  mutableStateOf("")

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
                userSession.users.add(User(name, password))
                userSession.saveUserSession(name,password)
                resetInput()
                return true
        } else {
            resetInput()
            isError = true
        }
        return false
    }
    fun userExist(): Boolean {
        for (user in userSession.users) {
            if (user.name == name) {
                errorMessage = "Username already exists"
                return true
            }
        }
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