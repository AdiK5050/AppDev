package com.adi_chat.app

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.lifecycle.viewmodel.compose.viewModel
import com.adi_chat.app.storage.UserSession
import com.adi_chat.app.shared_viewmodels.SharedViewModel

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "KotlinProjectDesktop",
    ) {
        MaterialTheme(
            colorScheme = darkColorScheme(),
        ) {
            Surface(modifier = Modifier
                .fillMaxSize()
                .imePadding()
                .displayCutoutPadding()
                ,
                color = MaterialTheme.colorScheme.secondaryContainer,
            ) {
                Column {
                    val appDatabase = getDatabaseBuilder()
                    val userSession = UserSession()
                    val sharedViewModel = viewModel { SharedViewModel(userSession) }
                    FullScreenLoader(sharedViewModel)
                    val destinations = Destinations(appDatabase, sharedViewModel)
                    destinations.CreateDestination()
                }
            }
        }
    }
}
@Composable
fun FullScreenLoader(
    sharedViewModel: SharedViewModel,
    bgColor: Color = Color(0x88000000)
) {

    if (sharedViewModel.isLoading.value) {
        Dialog(
            onDismissRequest = {},
            properties = DialogProperties(
                usePlatformDefaultWidth = false,
                dismissOnBackPress = false,
                dismissOnClickOutside = false,
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(bgColor),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    strokeWidth = 8.dp,
                    color = Color.Blue
                )
            }
        }
    }
}