package org.example.project.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class SignupViewModel : ViewModel() {
    var name by  mutableStateOf("")
    var password by  mutableStateOf("")
    val database = Database()

    var isError by  mutableStateOf(false)
    var errorMessage by  mutableStateOf("")

    fun signup() {
        isError = false
        errorMessage = ""

        if(name.trim().isNotEmpty() && password.trim().isNotEmpty()) {
           database.addUser(User(name, password)) // TODO: before adding user, check if they already exist!
        } else {
            isError = true
            errorMessage = "Sign up failed"
        }
    }
}