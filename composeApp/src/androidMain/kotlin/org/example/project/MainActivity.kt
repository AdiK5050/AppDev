package org.example.project

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(
                colorScheme = darkColorScheme(),
            ) {
                Surface(modifier = Modifier
                    .fillMaxSize()
                    .displayCutoutPadding()
                    .imePadding(),
                    color = MaterialTheme.colorScheme.secondaryContainer
                ) {
                    Column {
                        val destinations = Destinations()
                        destinations.CreateDestination()
                    }
                }
            }
        }
    }
}
@Preview(name = "Light Mode", showBackground = false,
    uiMode = Configuration.UI_MODE_TYPE_NORMAL
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
    )
@Composable
fun AppAndroidPreview() {
    MaterialTheme(
        colorScheme = darkColorScheme(),
    ) {
        Surface(modifier = Modifier,
            color = MaterialTheme.colorScheme.secondaryContainer
        ) {
            Column {
            }
        }
    }
}