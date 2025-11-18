package org.example.project.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.isShiftPressed
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wannaverse.imageselector.toImageBitmap
import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.arrow_back_24dp_e3e3e3_fill0_wght400_grad0_opsz24
import kotlinproject.composeapp.generated.resources.logout_24dp_e3e3e3_fill0_wght400_grad0_opsz24
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.Serializable
import org.example.project.Destination
import org.example.project.storage.MessageEntity
import org.example.project.viewmodels.MessageViewModel
import org.jetbrains.compose.resources.painterResource


@Serializable
object MessagePage : Destination

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewMessagePage(
    messageViewModel: MessageViewModel,
    onNavigateToProfile: () -> Unit,
    onNavigateToLogin: () -> Unit
) {

    val messageHistory by messageViewModel.messageHistory.collectAsStateWithLifecycle()

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
                    IconButton(
                        onClick = {
                            onNavigateToLogin()
                            messageViewModel.userSession.clearUserSession()
                        },
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
        content = { padding ->
            { Modifier.padding(5.dp) }
            MessageContent(
                modifier = Modifier.padding(padding),
                messageViewModel,
                onNavigateToProfile,
                messageHistory,
            )
        }
    )
}

@Composable
fun MessageContent(
    modifier: Modifier,
    messageViewModel: MessageViewModel,
    onNavigateToProfile: () -> Unit,
    messageHistory: List<MessageEntity>,
) {
    var profileClicked by remember { mutableStateOf(false) }
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
        modifier = Modifier.fillMaxSize().then(modifier)
    ) {
        LazyColumn(
            modifier = Modifier
                .weight(1f)
        ) {
            items(messageHistory) { messageEntity ->
                val message = messageEntity.message
                Text(text = message)
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
                    } else if (it.type == KeyEventType.KeyDown && it.key == Key.Enter && textFieldValue.text.isNotBlank()) {
                        messageViewModel.addMessage(textFieldValue.text)
                        textFieldValue = TextFieldValue("")
                        true
                    } else {
                        false // Let other events be handled normally
                    }
                }
        )
    }
}

@Composable
fun NewMessageCard(
    username: String,
    message: String,
    profilePic: ByteArray,
    profileClicked: Boolean,
    onNavigateToProfile: () -> Unit
) {
//    var horizontalArrangement by remember { mutableStateOf(Arrangement.Start)}
//    Row(
//        modifier = Modifier
//            .padding(all = 8.dp)
//            .fillMaxWidth(),
//        horizontalArrangement = horizontalArrangement,
//    ){
//
//            ImageVisibility(profileClicked,profilePic, onNavigateToProfile)
//        var isExpanded by remember { mutableStateOf(false)}
//        val surfaceColor by animateColorAsState(
//            if(isExpanded) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
//        )
//        Column (
//            modifier = Modifier
//                .clickable {isExpanded = !isExpanded },
//        ) {
//            Text(
//                username,
//                color = MaterialTheme.colorScheme.primary,
//                style = MaterialTheme.typography.titleMedium
//            )
//            Spacer(modifier = Modifier.height(3.dp))
//
//            Surface(
//                shape = MaterialTheme.shapes.small,
//                shadowElevation = 1.dp,
//                color = surfaceColor,
//                modifier = Modifier.animateContentSize().padding(1.dp)
//            ) {
//                Text(
//                    message,
//                    modifier = Modifier.padding(all = 4.dp),
//                    maxLines = if(isExpanded) Int.MAX_VALUE else 1,
//                    style = MaterialTheme.typography.bodySmall
//                )
//            }
//        }
//    }
}

@Composable
fun ImageVisibility(
    profileClicked: Boolean,
    profilePic: ByteArray,
    onNavigateToProfile: () -> Unit
) {
    Image(
        bitmap = profilePic.toImageBitmap(),
        contentDescription = "A photo of a beauty.",
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .clickable(enabled = true, onClick = {
                onNavigateToProfile()
            })
    )
}
