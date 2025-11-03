package org.example.project

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.preat.peekaboo.image.picker.FilterOptions
import com.preat.peekaboo.image.picker.ImagePickerLauncher
import com.preat.peekaboo.image.picker.ResizeOptions
import com.preat.peekaboo.image.picker.SelectionMode
import com.preat.peekaboo.image.picker.rememberImagePickerLauncher
import com.preat.peekaboo.image.picker.toImageBitmap
import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.riasgremory
import kotlinx.serialization.Serializable
import org.example.project.viewmodels.Database
import org.jetbrains.compose.resources.painterResource



@Serializable
data class UserProfile(val name: String)

@Composable
fun ProfileScreen(name: String, database: Database, onNavigateToMessages: () -> Unit) {
    var friendStatus: Boolean by remember { mutableStateOf(true) }
    var text: String by remember { mutableStateOf("Add Friend") }
    var addPhoto by remember { mutableStateOf(false) }
    var photoAdded by remember { mutableStateOf(false) }
    var pickPhoto by remember { mutableStateOf(false) }
    var imageBitmap by remember { mutableStateOf(database.getProfilePic()) }

    Column(modifier = Modifier.padding().fillMaxSize()) {
        Button(
            shape = CircleShape, onClick = onNavigateToMessages,
            modifier = Modifier.clip(CircleShape).padding(10.dp),
            content = {
                Text("Back")
            })

        Image(
            bitmap = database.getProfilePic(),
            contentDescription = "A photo of a beauty.",
            modifier = Modifier
                .size(100.dp)
                .clickable(onClick = { addPhoto = true })
        )

        Text("Name: $name", modifier = Modifier.padding(start = 2.dp))

        AnimatedVisibility(visible = pickPhoto) {
            Dialog(
                onDismissRequest = { pickPhoto = false },
                properties = DialogProperties(
                    dismissOnBackPress = true,
                    dismissOnClickOutside = true
                )
            ) {
                val scope = rememberCoroutineScope()

                val singleImagePicker = rememberImagePickerLauncher(
                    selectionMode = SelectionMode.Single,
                    scope = scope,
                    onResult = { byteArrays ->
                        byteArrays.firstOrNull()?.let {
                            // Process the selected images' ByteArrays.
                            imageBitmap = it.toImageBitmap()
                            database.setProfilePic(imageBitmap)
                        }
                    }
                )
                Button(onClick = {
                    singleImagePicker.launch()
                    photoAdded = true
                }) {
                    Text("Pick")
                }
            }
        }
        AnimatedVisibility(visible = addPhoto) {
            Dialog(
                onDismissRequest = { addPhoto = false },
                properties = DialogProperties(
                    dismissOnBackPress = true,
                    dismissOnClickOutside = true
                )
            ) {
                Column(modifier = Modifier.padding(10.dp).background(color = Color.Black)) {
                    Text(
                        "Do you want to add a photo?",
                        modifier = Modifier.padding(5.dp).align(Alignment.CenterHorizontally)
                    )
                    Row {
                        Button(
                            onClick = {
                                pickPhoto = true
                                addPhoto = false
                            }, modifier = Modifier.padding(10.dp)
                        ) {
                            Text("Yes")
                        }
                        Spacer(modifier = Modifier.size(60.dp))
                        Button(
                            onClick = { addPhoto = false },
                            modifier = Modifier.padding(10.dp)
                        ) {
                            Text("No")
                        }
                    }
                    AnimatedVisibility(photoAdded) {
                        Text(
                            "Photo Added Successfully!",
                            modifier = Modifier.padding(5.dp).align(Alignment.CenterHorizontally)
                        )
                    }
                }
            }
        }
        Button(
            shape = CircleShape,
            onClick = {
                if (friendStatus) {
                    text = "Remove Friend"
                    friendStatus = false
                } else {
                    text = "Add Friend"
                    friendStatus = true
                }
            },
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            content = {
                Text(text)
            }
        )
    }

}

/*
@Composable
fun ProfileScreen(name: String, onNavigateToMessages: () -> Unit) {
    var friendStatus: Boolean by remember { mutableStateOf(true) }
    var text: String by remember { mutableStateOf("Add Friend") }

    Column (
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        Button(
            shape = CircleShape, onClick = onNavigateToMessages,
            modifier = Modifier.clip(CircleShape).padding(10.dp),
            content = {
                Text("Back")
            })

            Image(
                painter = painterResource(Res.drawable.riasgremory),
                contentDescription = "A photo of a beauty.",
                modifier = Modifier
                    .size(100.dp)
            )

        Text("Name: $name", modifier = Modifier.padding(start = 2.dp))

        Button(
            shape = CircleShape,
            onClick = {
                if (friendStatus) {
                    text = "Remove Friend"
                    friendStatus = false
                } else {
                    text = "Add Friend"
                    friendStatus = true
                }
            },
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            content = {
                Text(text)
            }
        )
    }
}
*/
