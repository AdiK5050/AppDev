package org.example.project

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.lifecycle.viewmodel.compose.viewModel
import org.example.project.storage.UserSession
import org.example.project.viewmodels.SharedViewModel

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
                    val destinations = Destinations(appDatabase, sharedViewModel)
                    destinations.CreateDestination()
                }
            }
        }
    }
}