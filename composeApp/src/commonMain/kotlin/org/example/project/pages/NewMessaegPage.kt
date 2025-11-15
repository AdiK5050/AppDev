package org.example.project.pages

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.isShiftPressed
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.arrow_back_24dp_e3e3e3_fill0_wght400_grad0_opsz24
import kotlinproject.composeapp.generated.resources.logout_24dp_e3e3e3_fill0_wght400_grad0_opsz24
import kotlinx.serialization.Serializable
import org.example.project.viewmodels.Database
import org.example.project.viewmodels.Message
import org.example.project.viewmodels.MessageViewModel
import org.jetbrains.compose.resources.painterResource


@Serializable
object MessagePage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewMessagePage(database: Database,
                   messageViewModel: MessageViewModel = viewModel { MessageViewModel(database) },
                   onNavigateToProfile: () -> Unit,
                   onNavigateToLogin: () -> Unit)
    {
        LaunchedEffect(Unit) {
            messageViewModel.initMessageHistory()
        }
        val messageHistory = remember { messageViewModel.messageHistory }

    Scaffold(
        modifier = Modifier
            .background(color = Color.Black)
            .fillMaxSize()
            .padding(5.dp)
            .imePadding(),
        topBar = {
            TopAppBar(
                modifier = Modifier.background(color = Color.Black),
                title = { Text("New Message") },
                navigationIcon = @Composable {
                    IconButton(
                        onClick = {},
                        content = {
                            Icon(
                                painter = painterResource(Res.drawable.arrow_back_24dp_e3e3e3_fill0_wght400_grad0_opsz24),
                                contentDescription = "Back",
                                tint = Color.White,
                            )
                        }
                    )
                },
                actions = {
                    IconButton (
                        onClick = {
                            onNavigateToLogin()
                            database.clearUserSession()},
                        content = {
                            Icon(
                                painter = painterResource(Res.drawable.logout_24dp_e3e3e3_fill0_wght400_grad0_opsz24),
                                contentDescription = "Logout",
                                tint = Color.White
                            )
                        }
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Black,
                    titleContentColor = Color.White
                )
            )
        },
        content = { padding -> {Modifier.padding(5.dp)}
            MessageContent(
                messageViewModel,
                onNavigateToProfile,
                messageHistory,
            )
        }
    )
}

@Composable
fun MessageContent(
    messageViewModel: MessageViewModel,
    onNavigateToProfile: () -> Unit,
    messageHistory: List<Message>,
) {
    var textFieldValue by remember { mutableStateOf(TextFieldValue("")) }
    var buttonPressed by remember { mutableStateOf(false) }
    val enterIcon = @Composable {
        Button(
            modifier = Modifier
                .padding(10.dp),
            shape = CircleShape,
            onClick = (
                    {
                        buttonPressed = !buttonPressed
                        if (buttonPressed) messageViewModel.addMessage(textFieldValue.text) else null
                    }
                    )
        ) {
            Text("Enter")
            if (buttonPressed) {
                textFieldValue = TextFieldValue("")
            } else null
            buttonPressed = false
        }
    }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .weight(1f)
        ) {
            items(messageHistory) { message ->
                NewMessageCard(message, onNavigateToProfile)
            }
        }
        OutlinedTextField(
            value = textFieldValue,
            onValueChange = { newvalue -> textFieldValue = newvalue },
            label = { Text("Enter a message") },
            readOnly = false,
            trailingIcon = if (textFieldValue.text.isNotBlank()) enterIcon else null,
            modifier = Modifier
                .fillMaxWidth()
                .onPreviewKeyEvent {
                    if (it.key == Key.Enter && it.isShiftPressed && it.type == KeyEventType.KeyDown) {
                        val currentText = textFieldValue.text
                        val cursorPosition = textFieldValue.selection.start
                        val newText =
                            currentText.substring(0, cursorPosition) + "\n" + currentText.substring(
                                cursorPosition
                            )
                        textFieldValue = TextFieldValue(
                            text = newText,
                            selection = TextRange(cursorPosition + 1)
                        )
                        true // Consume the event
                    }
                    else if(it.type == KeyEventType.KeyDown && it.key == Key.Enter && textFieldValue.text.isNotBlank()) {
                        messageViewModel.addMessage(textFieldValue.text)
                        textFieldValue = TextFieldValue("")
                        true
                    }
                    else {
                        false // Let other events be handled normally
                    }
                }
        )
    }
}
@Composable
fun NewMessageCard(msg : Message, onNavigateToProfile: () -> Unit) {
    var horizontalArrangement by remember { mutableStateOf(Arrangement.Start)}
    var profilePic by remember { mutableStateOf(msg.profilePic)}
    if(msg.action == "receiver")
        horizontalArrangement = Arrangement.End
    else if(msg.action == "sender")
        horizontalArrangement = Arrangement.Start
    Row(
        modifier = Modifier
            .padding(all = 8.dp)
            .fillMaxWidth(),
        horizontalArrangement = horizontalArrangement,
    ){
        if(profilePic != null)
            ImageVisibility(profilePic as ImageBitmap, onNavigateToProfile)
        var isExpanded by remember { mutableStateOf(false)}
        val surfaceColor by animateColorAsState(
            if(isExpanded) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
        )
        Column (
            modifier = Modifier
                .clickable {isExpanded = !isExpanded },
        ) {
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
fun ImageVisibility(profilePic: ImageBitmap, onNavigateToProfile: () -> Unit) {
    Image(
        bitmap = profilePic,
        contentDescription = "A photo of a beauty.",
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .clickable(enabled = true, onClick = {onNavigateToProfile()})
    )
}
