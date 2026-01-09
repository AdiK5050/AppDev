package com.adi_chat.app.ui.app.contact

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.wannaverse.imageselector.toImageBitmap
import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.arrow_back_24dp_e3e3e3_fill0_wght400_grad0_opsz24
import kotlinproject.composeapp.generated.resources.download
import kotlinproject.composeapp.generated.resources.radio_button_checked_24dp_e3e3e3
import kotlinproject.composeapp.generated.resources.radio_button_unchecked_24dp_e3e3e3
import kotlinx.serialization.Serializable
import com.adi_chat.app.Destination
import com.adi_chat.app.storage.AppDatabase
import com.adi_chat.app.toByteArray
import com.adi_chat.app.ui.app.contact.viewmodels.ContactsViewModel
import com.adi_chat.app.shared.viewmodels.SharedViewModel
import org.jetbrains.compose.resources.imageResource
import org.jetbrains.compose.resources.painterResource

@Serializable
object Contacts: Destination

@Composable
fun ContactsPage(
    appDatabase: AppDatabase,
    sharedViewModel: SharedViewModel,
    contactsViewModel: ContactsViewModel = viewModel { ContactsViewModel(appDatabase,sharedViewModel) },
    onNavigateToChatList: () -> Unit,
    onNavigateToMessage: () -> Unit
    ) {
    var searchFieldValue by remember { mutableStateOf(TextFieldValue("")) }
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xff0D1114))
            .safeContentPadding()
    ) {
        Column {
            Header(
                searchFieldValue.text,
                contactsViewModel,
                onNavigateToChatList = {
                    onNavigateToChatList()
                },
                onNavigateToMessage = {
                    onNavigateToMessage()
                }
            )
            UserList(
                contactsViewModel
            )
        }
    }
}

@Composable
fun Header(
    searchFieldValue: String,
    contactsViewModel: ContactsViewModel,
    onNavigateToChatList: () -> Unit,
    onNavigateToMessage: () -> Unit
) {
    var searchFieldValue by remember { mutableStateOf(searchFieldValue)}
    var channelNameValue by remember {mutableStateOf("")}
    var showDialog by remember {mutableStateOf(false)}

    Column {
        Row(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth()
        ) {
            IconButton(
                modifier = Modifier
                    .padding(),
                onClick = {
                    onNavigateToChatList()
                },
            ) {
                Icon(
                    painter = painterResource(Res.drawable.arrow_back_24dp_e3e3e3_fill0_wght400_grad0_opsz24),
                    contentDescription = "Back",
                    tint = Color.White,

                )
            }
            Box(
                modifier = Modifier
                    .padding()
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "New Message",
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp,
                    color = Color.White,
                )
            }
            AnimatedVisibility(contactsViewModel.create.value) {
                Box(
                    modifier = Modifier
                        .padding(5.dp),
                    contentAlignment = Alignment.Center
                ) {
                Text(
                    "Create",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    color = Color.White,
                    modifier = Modifier
                        .padding()
                        .clickable(
                            onClick = { showDialog = true }
                        )
                )
                    }
            }
        }
        TextField(
            value = searchFieldValue,
            onValueChange = { newValue -> searchFieldValue = newValue },
            label = { Text("To: Search your friends") },
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
                .background(Color.LightGray)
        )
        AnimatedVisibility(showDialog) {
            Dialog(
                onDismissRequest = { showDialog = false },
                properties = DialogProperties(
                    dismissOnClickOutside = true,
                    dismissOnBackPress = true,
                )
            ) {
                Column {
                    Text(
                        "Create Channel",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.White,
                        modifier = Modifier
                            .padding(10.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                    TextField(
                        value = channelNameValue,
                        onValueChange = { newvalue -> channelNameValue = newvalue },
                        label = { "Channel Name" },
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth()
                    )
                    Button(
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth(1f),
                        shape = RoundedCornerShape(20),
                        onClick = {
                            showDialog = false
                            contactsViewModel.createChannel(channelNameValue)
                            onNavigateToMessage()
                        }
                    ) {
                        Text("Create Channel")
                    }
                }
            }
        }
    }
}

@Composable
fun UserList (
    contactsViewModel: ContactsViewModel,
) {
    val userList by contactsViewModel.users.collectAsStateWithLifecycle()
    Box(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxSize()
    ) {
        LazyColumn {
            items(userList) { user ->
                UserCard(
                    user.username,
                    user.profilePic!!,
                    onSelect = {
                        contactsViewModel.addMember(user.userID)
                    },
                    onUnselect = {
                        contactsViewModel.removeMember(user.userID)
                    }
                )
            }
        }
    }
}

@Composable
fun UserCard(
    username: String,
    image: ByteArray,
    onSelect: () -> Unit,
    onUnselect: () -> Unit,
) {
    var image: ByteArray? by remember {mutableStateOf(image)}
    if(image == null || image.contentEquals(ByteArray(0))) image = imageResource(Res.drawable.download).toByteArray()
    var buttonClicked by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .background(color = Color.Black, shape = RoundedCornerShape(20))
            .clickable(
                onClick = {
                    buttonClicked = !buttonClicked
                    if(buttonClicked) onSelect()
                    else onUnselect()
                }
            )
    ) {

        Image(
            bitmap = image!!.toImageBitmap(),
            contentDescription = "Image Of Contact",
            modifier = Modifier
                .clip(shape = CircleShape)
                .size(50.dp)
        )
        Text(
            username,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            color = Color.White,
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
                .weight(1f)
        )
        Icon(
            painter =
                if(buttonClicked) painterResource(Res.drawable.radio_button_checked_24dp_e3e3e3)
                else painterResource(Res.drawable.radio_button_unchecked_24dp_e3e3e3)
                ,
            contentDescription = "Select Contact",
            tint = Color.White,
            modifier = Modifier
                .padding(10.dp)
        )
    }
}
