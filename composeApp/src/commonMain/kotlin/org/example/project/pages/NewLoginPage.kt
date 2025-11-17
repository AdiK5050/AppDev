package org.example.project.pages

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.visibility_24dp_e3e3e3_fill0_wght400_grad0_opsz24
import kotlinproject.composeapp.generated.resources.visibility_off_24dp_e3e3e3_fill0_wght400_grad0_opsz24
import kotlinx.serialization.Serializable
import org.example.project.Destination
import org.example.project.storage.AppDatabase
import org.example.project.storage.UserSession
import org.example.project.viewmodels.LoginViewModel
import org.jetbrains.compose.resources.painterResource

@Serializable
object NewLogin : Destination

@Composable
fun NewLogin(
    userSession: UserSession,
    appDatabase: AppDatabase,
    loginViewModel: LoginViewModel = viewModel { LoginViewModel(userSession, appDatabase) },
    onNavigateToMessages: () -> Unit,
    onNavigateToSignup: () -> Unit
) {
    LaunchedEffect(Unit) {
        loginViewModel.getAllUsers()
    }
    var loginFailed by remember { mutableStateOf(false) }
    Surface(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.background)
            .padding(5.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .imePadding()
                .fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Column(
                modifier = Modifier,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    "Adi Chat",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White,
                    modifier = Modifier.padding(),
                    textAlign = TextAlign.Center
                )

                Text(
                    "A cool new way to chat with your friends",
                    fontSize = 10.sp,
                    color = Color.White,
                    modifier = Modifier.padding(),
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.size(10.dp))
                Column(
                    modifier = Modifier
                        .border(
                            width = 1.dp,
                            color = Color.White,
                            shape = RoundedCornerShape(5.dp)
                        )
                ) {
                    NewLogInLayout(
                        name = loginViewModel.name,
                        password = loginViewModel.password,
                        onKeyboardDone =
                            {
                                loginViewModel.login()
                                if (!loginViewModel.isError) {
                                    onNavigateToMessages()
                                } else {
                                    loginFailed = loginViewModel.isError
                                }
                            },
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
                }
                Spacer(Modifier.size(2.dp))
                AnimatedVisibility(loginFailed) {
                    Column {
                        Text(
                            text = "Log-In Failed",
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = loginViewModel.errorMessage,
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                    }
                }
                Button(
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(5.dp),
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.surfaceContainerHighest),
                    onClick = {
                        loginViewModel.login()
                        if (!loginViewModel.isError) {
                            onNavigateToMessages()
                        } else {
                            loginFailed = loginViewModel.isError
                        }
                    }) {
                    Text("Log-In", color = Color.White, fontWeight = FontWeight.Bold)
                }

                Spacer(Modifier.size(5.dp))
                Row {
                    Text("Don't have an account?")
                    Spacer(Modifier.size(2.dp))
                    Text(
                        "Sign-Up",
                        modifier = Modifier
                            .padding()
                            .clickable {
                                onNavigateToSignup()
                            },
                        color = Color.White,
                    )
                }
            }
        }
    }
}

@Composable
fun NewLogInLayout(
    name: String,
    password: String,
    onKeyboardDone: () -> Unit,
    onUserNameChanged: (String) -> Unit,
    onUserPasswordChanged: (String) -> Unit,
) {
    var visibility by remember { mutableStateOf(false) }
    TextField(
        onValueChange = onUserNameChanged,
        value = name,
        label = { Text("Enter Your Name") },
        readOnly = false,
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = { onKeyboardDone() }
        ),
        colors = TextFieldDefaults.colors(
            Color.White, Color.White,
            focusedContainerColor = MaterialTheme.colorScheme.background,
            unfocusedContainerColor = MaterialTheme.colorScheme.background,
            errorTextColor = Color.Red, errorLabelColor = Color.Red,
        )
    )
    TextField(
        onValueChange = onUserPasswordChanged,
        value = password,
        label = { Text("Enter Your Password") },
        readOnly = false,
        visualTransformation = if (!visibility) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done,
            keyboardType = if (visibility) KeyboardType.Text else KeyboardType.Password
        ),
        keyboardActions = KeyboardActions(
            onDone = { onKeyboardDone() }
        ),
        colors = TextFieldDefaults.colors(
            Color.White, Color.White,
            focusedContainerColor = MaterialTheme.colorScheme.background,
            unfocusedContainerColor = MaterialTheme.colorScheme.background,
            errorTextColor = Color.Red, errorLabelColor = Color.Red,
        ),
        trailingIcon = @Composable {
            IconButton(
                onClick = { visibility = !visibility },
                content = {
                    Icon(
                        painter = if (!visibility) painterResource(Res.drawable.visibility_off_24dp_e3e3e3_fill0_wght400_grad0_opsz24)
                        else painterResource(Res.drawable.visibility_24dp_e3e3e3_fill0_wght400_grad0_opsz24),
                        contentDescription = "visibility",
                        tint = Color.White,
                    )
                }
            )
        }
    )
}