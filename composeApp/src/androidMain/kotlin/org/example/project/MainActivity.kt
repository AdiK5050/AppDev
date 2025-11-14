package org.example.project

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
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
import com.wannaverse.imageselector.registerImageSelectorLauncher
import com.wannaverse.imageselector.setImageSelectorActivity


class MainActivity : ComponentActivity() {
    lateinit var requestPermissionLauncher: ActivityResultLauncher<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //wannaverse image selector instance creation
        setImageSelectorActivity(this)
        registerImageSelectorLauncher()

        //requesting app permissions
        requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) {}
        requestPermissionLauncher.launch(android.Manifest.permission.CAMERA)
        requestPermissionLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)


        setContent {
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