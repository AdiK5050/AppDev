package org.example.project

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import kotlinx.serialization.Serializable

@Serializable
object Login


@Composable
fun Login(appViewModel: AppViewModel,onNavigateToMessages: () -> Unit, onNavigateToSignup: () -> Unit) {

    val appUiState: AppUiState by appViewModel.uiState.collectAsState()
    var loginFailed by remember { mutableStateOf(false)}
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(50.dp)
            .imePadding(),
        Arrangement.Center,
        Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .padding(5.dp)
                .align(Alignment.CenterHorizontally)
        ) { Text("Log-In Here") }

        Spacer(Modifier.size(10.dp))
        LogInLayout(
            name = appViewModel.name,
            password = appViewModel.password,
            onKeyboardDone = {
                appViewModel.checkUserInfo()
                loginFailed = !appUiState.logInSuccessful},
            onUserNameChanged = {
                appViewModel.updateUserName(it)
                loginFailed = false },
            onUserPasswordChanged = {
                appViewModel.updatePassword(it)
                loginFailed = false},
        )
        Button(onClick = {
            appViewModel.checkUserInfo()
            loginFailed = !appUiState.logInSuccessful
        }) {
            Text("Log-In")
            if(appUiState.logInSuccessful) {
                onNavigateToMessages()
            }
        }
        Spacer(Modifier.size(5.dp))
        AnimatedVisibility(loginFailed) {
            Text(
                text = "Log-In Failed",
                color = MaterialTheme.colorScheme.error
            )
        }
        Spacer(Modifier.size(10.dp))

        Text("Don't have an account?")
        Button(onClick =  onNavigateToSignup ){
            Text("Sign-Up")
        }
    }
}

@Composable
fun LogInLayout(
    name: String,
    password: String,
    onKeyboardDone: () -> Unit,
    onUserNameChanged: (String) -> Unit,
    onUserPasswordChanged: (String) -> Unit,
) {
    OutlinedTextField(
        onValueChange = onUserNameChanged,
        value = name,
        label = { Text("Enter Your Name") },
        readOnly = false,
        modifier = Modifier
            .padding()
            .background(color = MaterialTheme.colorScheme.surface)
            .onKeyEvent { event ->
                if (event.type == KeyEventType.KeyDown && event.key == Key.Enter) {
                    onKeyboardDone()
                    true }
                else {
                    false
                }
            },
        keyboardOptions =  KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = { onKeyboardDone() }
        )
    )
    OutlinedTextField(
        onValueChange = onUserPasswordChanged,
        value = password,
        label = { Text("Enter Your Password") },
        readOnly = false,
        modifier = Modifier
            .padding()
            .background(color = MaterialTheme.colorScheme.surface)
            .onKeyEvent { event ->
                if (event.type == KeyEventType.KeyDown && event.key == Key.Enter) {
                    onKeyboardDone()
                    true }
                else {
                    false
                }
            },
        keyboardOptions =  KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {onKeyboardDone()}
        )
    )
}
