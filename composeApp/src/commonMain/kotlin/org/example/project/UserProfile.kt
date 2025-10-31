package org.example.project

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.riasgremory
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNull.content
import org.jetbrains.compose.resources.painterResource

@Serializable
data class UserProfile(val name: String)

@Composable
fun ProfileScreen(name: String, onNavigateToMessages: () -> Unit) {
    var friendStatus: Boolean by remember { mutableStateOf(true) }
    var text: String by remember { mutableStateOf("Add Friend") }

    Dialog(
        onDismissRequest = {},
        properties = DialogProperties(usePlatformDefaultWidth = true),
        content = {
            Column {
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
    )
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
