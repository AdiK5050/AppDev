package com.adi_chat.app.ui.app.chat

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
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.isShiftPressed
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.wannaverse.imageselector.toImageBitmap
import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.arrow_back_24dp_e3e3e3_fill0_wght400_grad0_opsz24
import kotlinproject.composeapp.generated.resources.download
import kotlinx.serialization.Serializable
import com.adi_chat.app.Destination
import com.adi_chat.app.storage.AppDatabase
import com.adi_chat.app.storage.MessageEntity
import com.adi_chat.app.storage.UserSession
import com.adi_chat.app.ui.app.chat.viewmodels.ChannelMembersInfo
import com.adi_chat.app.ui.app.chat.viewmodels.MessageViewModel
import com.adi_chat.app.shared_viewmodels.SharedViewModel
import org.jetbrains.compose.resources.imageResource
import org.jetbrains.compose.resources.painterResource


@Serializable
object MessagePage : Destination

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewMessagePage(
    appDatabase: AppDatabase,
    userSession: UserSession,
    sharedViewModel: SharedViewModel,
    messageViewModel: MessageViewModel = viewModel { MessageViewModel(appDatabase,userSession,sharedViewModel) },
    onNavigateToProfile: () -> Unit,
    onNavigateToChatList: () -> Unit,
) {
    LaunchedEffect(Unit) {
        messageViewModel.initAll()
    }
    val messageHistory by messageViewModel.messageHistory.collectAsStateWithLifecycle()
    val channelMembers by remember {mutableStateOf(messageViewModel.channelMembers)}
    val channel by messageViewModel.channel.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier
            .background(color = Color.Black)
            .fillMaxSize()
            .padding(5.dp)
            .imePadding(),
        topBar = {
            TopAppBar(
                modifier = Modifier.background(color = Color.Black),
                title = { Text(channel.channelName)},
                navigationIcon = @Composable {
                    IconButton(
                        onClick = {
                            onNavigateToChatList()
                        },
                        content = {
                            Icon(
                                painter = painterResource(Res.drawable.arrow_back_24dp_e3e3e3_fill0_wght400_grad0_opsz24),
                                contentDescription = "Back",
                                tint = Color.White,
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
                messageHistory,
                channelMembers,
                onNavigateToProfile,
            )
        }
    )
}

@Composable
fun MessageContent(
    modifier: Modifier,
    messageViewModel: MessageViewModel,
    messageHistory: List<MessageEntity>,
    channelMembers: List<ChannelMembersInfo>,
    onNavigateToProfile: () -> Unit,
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
                NewMessageCard(
                    messageEntity,
                    channelMembers,
                    {
                        profileClicked = !profileClicked
                        messageViewModel.setCurrentUserID(messageEntity.senderID)
                    },
                    { onNavigateToProfile() }
                )
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
                        true
                    } else if (it.type == KeyEventType.KeyDown && it.key == Key.Enter && textFieldValue.text.isNotBlank()) {
                        messageViewModel.addMessage(textFieldValue.text)
                        textFieldValue = TextFieldValue("")
                        true
                    } else {
                        false
                    }
                }
        )
    }
}
@Composable
fun NewMessageCard(
    messageEntity: MessageEntity,
    channelMembers: List<ChannelMembersInfo>,
    onProfileClick: () -> Unit,
    onNavigateToProfile: () -> Unit
) {
    val channelMembers by remember { mutableStateOf(channelMembers )}
    var username by remember { mutableStateOf("user") }
    var profilePic: ByteArray? by remember { mutableStateOf(ByteArray(0))}
    val user = channelMembers.find { it.userID == messageEntity.senderID }
    if(user != null) {
        username = user.username
        profilePic = user.profilePic
    }
    Row(
        modifier = Modifier
            .padding(all = 8.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
    ){
        Image(
            bitmap = if(profilePic == null || profilePic.contentEquals(ByteArray(0)))
                imageResource(Res.drawable.download)
            else
                profilePic!!.toImageBitmap(),
            contentDescription = "A photo of a beauty.",
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .clickable(enabled = true, onClick = {
                    onProfileClick()
                    onNavigateToProfile()
                }),
            contentScale = ContentScale.Crop
        )
        var isExpanded by remember { mutableStateOf(false)}
        val surfaceColor by animateColorAsState(
            if(isExpanded) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
        )
        Column (
            modifier = Modifier
                .clickable {isExpanded = !isExpanded },
        ) {
            Text(
                username,
                modifier = Modifier.padding(all = 4.dp),
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
                    messageEntity.message,
                    modifier = Modifier.padding(all = 4.dp),
                    maxLines = if(isExpanded) Int.MAX_VALUE else 1,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}
