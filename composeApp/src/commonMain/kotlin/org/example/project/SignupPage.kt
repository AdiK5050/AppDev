package org.example.project

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import kotlinx.serialization.Serializable

data class UserInfo(val name: String, val password: String)

@Serializable
object SignUp

@Composable
fun SignUp(appViewModel: AppViewModel, onNavigateToLogin: () -> Unit) {
    val appUiState by appViewModel.uiState.collectAsState()

    var signupFailed by remember { mutableStateOf(false)}
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
        ) { Text("Sign-Up Here") }

        Spacer(Modifier.size(10.dp))

        SignUpLayout(
            name = appViewModel.name,
            password = appViewModel.password,
            onKeyboardDone = { appViewModel.addUserInfo() },
            onUserNameChanged = { appViewModel.updateUserName(it)},
            onUserPasswordChanged = { appViewModel.updatePassword(it)},
        )

        Button(onClick = {
            appViewModel.addUserInfo()
            signupFailed = !appUiState.signUpSuccessful
        }) {
            Text("Sign-Up")
            if(appUiState.signUpSuccessful) {
                onNavigateToLogin()
            }
        }

        AnimatedVisibility(signupFailed) {
            Text(
                text = "Sign-Up Failed",
                color = MaterialTheme.colorScheme.error
            )
        }
        Spacer(Modifier.size(10.dp))

        Text("Already have an account?")
        Button(onClick = onNavigateToLogin) {
            Text("Log-In")
        }
    }
}

@Composable
fun SignUpLayout(
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
            .background(color = MaterialTheme.colorScheme.surface),
        keyboardOptions =  KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = { onKeyboardDone()}
        )
    )
    OutlinedTextField(
        onValueChange = onUserPasswordChanged,
        value = password,
        label = { Text("Enter Your Password") },
        readOnly = false,
        modifier = Modifier
            .padding()
            .background(color = MaterialTheme.colorScheme.surface),
        keyboardOptions =  KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {onKeyboardDone()}
        )
    )
}
