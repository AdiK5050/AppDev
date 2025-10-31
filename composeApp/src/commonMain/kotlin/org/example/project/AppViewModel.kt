package org.example.project

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AppViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(AppUiState())
    val uiState: StateFlow<AppUiState> = _uiState.asStateFlow()
    //var userInfoList: MutableList<UserInfo> = mutableListOf()
    var name by  mutableStateOf("")
    var password by  mutableStateOf("")

    fun updateUserName(name: String) {
        this.name = name
    }
    fun updatePassword(password: String) {
        this.password = password
    }
    fun checkUserInfo()  {
        for (user in uiState.value.userInfoList) {
            if (user.name.equals(name.trim()) && user.password.equals(password.trim())){
                _uiState.update { currentState ->
                    currentState.copy(logInSuccessful = true)
                }
            }
        updateUserName("")
        updatePassword("")
        }
    }
    fun addUserInfo() {
        if(name.trim().isNotEmpty() || password.trim().isNotEmpty()) {
            _uiState.update { currentState ->
                currentState.copy(signUpSuccessful = true)
            }
        }
        updateUserName("")
        updatePassword("")
    }
    fun start() {
        _uiState.value = AppUiState()
    }
    init{
        start()
    }
}