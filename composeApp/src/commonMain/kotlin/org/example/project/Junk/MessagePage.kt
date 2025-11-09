package org.example.project.Junk


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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

import kotlinx.serialization.Serializable
import org.example.project.viewmodels.Database


@Serializable
object Message

@Composable
fun MessageCard(msg: OldMessages, database: Database, onNavigateToProfile: () -> Unit) {

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
fun Messages(messages: List<OldMessages>, database: Database, onNavigateToProfile: () -> Unit, onNavigateToLogin: ()-> Unit){
    var buttonPressed by remember { mutableStateOf(false) }
    var value by remember{ mutableStateOf("")}

    Surface(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        ) {
            if (buttonPressed) addMessage(value) else null
            items(messages) { message ->
                MessageCard(message, database, onNavigateToProfile)
            }
        }
        val label = "Enter a message"
        val icon = @Composable {
            Button(
                modifier = Modifier
                    .padding(10.dp),
                shape = CircleShape,
                onClick = ({ buttonPressed = !buttonPressed })
            ) {
                Text("Enter")
                if (buttonPressed) value = ""
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
                trailingIcon = if (value.isNotBlank()) icon else null,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.surface),
            )
        }
    }
}
fun addMessage(msg: String) {
    val message = OldMessages("Rias", msg)
    SampleData.conversationSample.add(message)
}
