package org.example.project

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import com.wannaverse.imageselector.toImageBitmap
import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.arrow_back_24dp_e3e3e3_fill0_wght400_grad0_opsz24
import kotlinx.serialization.Serializable
import org.example.project.viewmodels.Database
import org.example.project.viewmodels.ProfilePageViewModel
import org.jetbrains.compose.resources.painterResource

@Serializable
object NewProfilePage
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewProfilePage(database: Database,
                   profilePageViewModel: ProfilePageViewModel = viewModel {
                       ProfilePageViewModel(
                           database
                       )
                   },
                   onNavigateToMessages: () -> Unit ){
    var pickPhoto by remember { mutableStateOf(false) }
    val image = remember { profilePageViewModel.image }
    val bitmap = image.value?.bytes?.toImageBitmap()
    val name = remember { profilePageViewModel.name.value }
    var friendAdded = remember { profilePageViewModel.friendAdded.value }
    val friendStatus = remember {mutableStateOf("Add Friend")}

    LaunchedEffect(Unit) {
        profilePageViewModel.init()
    }
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(2.dp)
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        Column {
            TopAppBar(
                modifier = Modifier.background(color = Color.Black),
                title = { Text("Profile Page") },
                navigationIcon = @Composable {
                    IconButton(
                        onClick = { onNavigateToMessages() },
                        content = {
                            Icon(
                                painter = painterResource(Res.drawable.arrow_back_24dp_e3e3e3_fill0_wght400_grad0_opsz24),
                                contentDescription = "Back",
                                tint = Color.White,
                            )
                        }
                    )
                }
            )
            Row {
                if (bitmap != null) {
                    Image(
                        bitmap = bitmap,
                        contentDescription = "UserProfilePic",
                        modifier = Modifier
                            .clickable(onClick = {
                                pickPhoto = true
                            })
                            .clip(shape = RectangleShape)
                            .padding(2.dp)
                            .size(100.dp),
                    )
                    Text("Name:$name\nContact:      ", modifier = Modifier.align(Alignment.CenterVertically))
                }
            }
            Button(
                onClick = {
                    friendAdded = !friendAdded
                    if(friendAdded) friendStatus.value = "Remove Friend" else friendStatus.value = "Add Friend"
                          },
                modifier = Modifier.padding(10.dp).fillMaxWidth(),
            ) {

                Text(friendStatus.value)
            }
            AnimatedVisibility(pickPhoto) {
                Dialog(
                    onDismissRequest = { pickPhoto = false },
                    properties = DialogProperties(
                        dismissOnBackPress = true,
                        dismissOnClickOutside = true,
                    ),
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                shape = MaterialTheme.shapes.medium,
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.primary
                            )
                            .shadow(elevation = 10.dp, shape = MaterialTheme.shapes.medium)
                            .background(color = MaterialTheme.colorScheme.background)
                            .padding(10.dp)
                    ) {
                        Text("Do you want to upload a new profile picture?")
                        Row {
                            Button(
                                onClick = {
                                    if (pickPhoto) profilePageViewModel.chooseImage()
                                    pickPhoto = false
                                },
                                modifier = Modifier.weight(0.5f)
                            ) {
                                Text("Yes")
                            }
                            Spacer(modifier = Modifier.padding(10.dp).size(30.dp))
                            Button(
                                onClick = { pickPhoto = false },
                                modifier = Modifier.weight(0.5f)
                            ) {
                                Text("No")
                            }
                        }
                    }
                }
            }
        }
    }
}