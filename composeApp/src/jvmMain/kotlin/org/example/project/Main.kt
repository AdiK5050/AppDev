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
                    val destinations = Destinations(appDatabase)
                    destinations.CreateDestination()
                }
            }
        }
    }
}