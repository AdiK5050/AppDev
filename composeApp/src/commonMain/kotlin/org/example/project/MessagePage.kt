package org.example.project


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.riasgremory
import kotlinx.serialization.Serializable
import org.example.project.viewmodels.Database
import org.jetbrains.compose.resources.imageResource


data class Message(val author: String, val body: String)

@Serializable
object Text

@Composable
fun MessageCard(msg : Message, database: Database, onNavigateToProfile: () -> Unit) {

    Row(
        modifier = Modifier.padding(all = 8.dp)
    ){
        Image(
            bitmap = database.getProfilePic(),
            contentDescription = "A photo of a beauty.",
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .clickable(enabled = true, onClick = onNavigateToProfile)
        )
        var isExpanded by remember { mutableStateOf(false)}
        val surfaceColor by animateColorAsState(
            if(isExpanded) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
        )
        Column (modifier = Modifier.clickable {isExpanded = !isExpanded}) {
            Text(
                msg.author,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleMedium
                )
            Spacer(modifier = Modifier.height(3.dp))

            Surface(
                shape = MaterialTheme.shapes.small,
                shadowElevation = 1.dp,
                color = surfaceColor,
                modifier = Modifier.animateContentSize().padding(1.dp)
            ) {
                Text(
                    msg.body,
                    modifier = Modifier.padding(all = 4.dp),
                    maxLines = if(isExpanded) Int.MAX_VALUE else 1,

                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Composable
fun Messages(messages: List<Message>, database: Database, onNavigateToProfile: () -> Unit){
    var buttonPressed by remember { mutableStateOf(false) }
    var value by remember{ mutableStateOf("")}

    Surface{
        LazyColumn(
            modifier = Modifier
                .height(715.dp),
        ) {
            if(buttonPressed) addMessage(value) else null
            items(messages) { message ->
                MessageCard(message, database,onNavigateToProfile)
            }
        }
        val label = "Enter a message"
        val icon = @Composable {
            Button(
                modifier = Modifier
                    .padding(10.dp),
                shape = CircleShape,
                onClick = (
                        { buttonPressed = !buttonPressed })
            ) {
                Text("Enter")
                //if(buttonPressed) AddMessage(value)
                if(buttonPressed) value = ""
                buttonPressed = false
            }
        }
        Row(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxSize(),
            verticalAlignment = Alignment.Bottom
        ) {
            OutlinedTextField(
                onValueChange = { value = it },
                value = value,
                label = { Text(label) },
                readOnly = false,
                trailingIcon = if(value.isNotBlank()) icon else null,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.surface),
            )
        }
    }
}
fun addMessage(msg: String) {
    val message = Message("Rias", msg)
    SampleData.conversationSample.add(message)
}
@Composable
fun Profile(show: Boolean) {
    var showProfile by remember { mutableStateOf(false) }
    showProfile = show
    AnimatedVisibility(showProfile) {
        NavigationBar (
            modifier = Modifier
                .fillMaxWidth()
                .padding(),
        ) {
            Column (
                modifier = Modifier

                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text("Hello")
            }
        }
    }
    }

@Preview
@Composable
fun PreviewMyCard() {

}
/*
@Composable
@Preview
fun App() {
    var showContent by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .safeContentPadding()
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Button(onClick = {showContent = !showContent}) {
            Text("Fuck Me")
        }
    }
    AnimatedVisibility(showContent) {
        val greeting = remember { Greeting().greet() }
        Column (
            modifier = Modifier

                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text("$greeting")
        }
    }
*/

  /*
    MaterialTheme {
        var showContent by remember { mutableStateOf(false) }
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primaryContainer)
                .safeContentPadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Button(onClick = { showContent = !showContent }) {
                Text("Click me!")
            }
            AnimatedVisibility(showContent) {
                val greeting = remember { Greeting().greet() }
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Image(painterResource(Res.drawable.compose_multiplatform), null)
                    Text("Compose: $greeting")
                }
            }
        }
    }*/
