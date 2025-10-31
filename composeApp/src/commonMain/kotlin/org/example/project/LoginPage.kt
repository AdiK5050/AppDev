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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.serialization.Serializable
import org.example.project.viewmodels.Database
import org.example.project.viewmodels.LoginViewModel

@Serializable
object Login


@Composable
fun Login(database: Database,loginViewModel: LoginViewModel = viewModel { LoginViewModel(database = database) }, onNavigateToMessages: () -> Unit, onNavigateToSignup: () -> Unit) {

    var loginFailed by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp)
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
            name = loginViewModel.name,
            password = loginViewModel.password,
            onKeyboardDone =
                { loginViewModel.login()
                     if (!loginViewModel.isError) {
                         onNavigateToMessages()
                     }else {
                         loginFailed = loginViewModel.isError
                     }},
            onUserNameChanged = {
                loginViewModel.name = it
                loginViewModel.errorMessage = ""
                loginViewModel.isError = false
                loginFailed = loginViewModel.isError
                },
            onUserPasswordChanged = {
                loginViewModel.password = it
                loginViewModel.errorMessage = ""
                loginViewModel.isError = false
                loginFailed = loginViewModel.isError
                },
        )
        Button(onClick = {
            loginViewModel.login()
            if (!loginViewModel.isError) {
                onNavigateToMessages()
            }else {
                loginFailed = loginViewModel.isError
            }
        }) {
            Text("Log-In")

        }
        Spacer(Modifier.size(5.dp))
        AnimatedVisibility(loginFailed) {
            Column {
                Text(
                    text = "Log-In Failed",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Text(
                    text =  loginViewModel.errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
        Spacer(Modifier.size(2.dp))

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
            .background(color = MaterialTheme.colorScheme.surface),
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
            .background(color = MaterialTheme.colorScheme.surface),
        keyboardOptions =  KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {onKeyboardDone()}
        )
    )
}
