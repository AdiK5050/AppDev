package com.adi_chat.app

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import com.adi_chat.app.shared.viewmodels.SharedViewModel
import com.adi_chat.app.storage.UserSession
import com.adi_chat.app.ui.theme.AppTheme
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
        requestPermissionLauncher.launch(Manifest.permission.CAMERA)
        requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)


        setContent {
            AppTheme(
                darkTheme = isSystemInDarkTheme()
            ) {
                Surface(modifier = Modifier
                    .fillMaxSize()
                    .imePadding()
                    .displayCutoutPadding(),
                    color = MaterialTheme.colorScheme.secondaryContainer,
                ) {
                    Column {
                        val appDatabase = getDatabaseBuilder(this@MainActivity)
                        val userSession = UserSession()
                        val sharedViewModel = viewModel { SharedViewModel(userSession) }
                        FullScreenLoader(sharedViewModel)
                        val destinations = Destinations(appDatabase,sharedViewModel)
                        destinations.CreateDestination()
                    }
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