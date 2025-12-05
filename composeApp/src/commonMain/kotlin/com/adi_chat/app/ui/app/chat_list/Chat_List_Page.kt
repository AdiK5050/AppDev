package com.adi_chat.app.ui.app.chat_list

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.wannaverse.imageselector.toImageBitmap
import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.add_circle_24dp_e3e3e3
import kotlinproject.composeapp.generated.resources.do_not_disturb_on_24dp_e3e3e3_fill0_wght400_grad0_opsz24
import kotlinproject.composeapp.generated.resources.download
import kotlinproject.composeapp.generated.resources.home_work_24dp_e3e3e3
import kotlinproject.composeapp.generated.resources.logout_24dp_e3e3e3_fill0_wght400_grad0_opsz24
import kotlinproject.composeapp.generated.resources.notifications_24dp_e3e3e3
import kotlinproject.composeapp.generated.resources.person_add_24dp_e3e3e3_fill0_wght400_grad0_opsz24
import kotlinproject.composeapp.generated.resources.search_24dp_e3e3e3_fill0_wght400_grad0_opsz24
import kotlinx.serialization.Serializable
import com.adi_chat.app.Destination
import com.adi_chat.app.storage.AppDatabase
import com.adi_chat.app.storage.UserSession
import com.adi_chat.app.toByteArray
import com.adi_chat.app.ui.app.chat_list.viewmodels.ChatListViewModel
import com.adi_chat.app.shared_viewmodels.SharedViewModel
import org.jetbrains.compose.resources.imageResource
import org.jetbrains.compose.resources.painterResource

@Serializable
object ChatList: Destination

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatList(
    appDatabase: AppDatabase,
    sharedViewModel: SharedViewModel,
    userSession: UserSession,
    chatListViewModel: ChatListViewModel = viewModel { ChatListViewModel(appDatabase, userSession, sharedViewModel) },
    onNavigateToChatList: () -> Unit,
    onNavigateToContacts: () -> Unit,
    onNavigateToMessage: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onNavigateToLogin: () -> Unit,
    ) {
    LaunchedEffect(Unit) {
        chatListViewModel.initAll()
    }
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar =  {
            TopBar(
                onSearch = {},
                onAddFriend = {},
                onNavigateToLogin = {
                    chatListViewModel.clearUserSession()
                    onNavigateToLogin()
                }
            )
        },
        bottomBar =  {
            BottomBar(
                chatListViewModel,
                onClickHome = { onNavigateToChatList() },
                onClickNotifications = { /*TODO*/ },
                onClickProfile = { onNavigateToProfile() },
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onNavigateToContacts()
                },
                modifier = Modifier
                    .padding(5.dp),
                shape = FloatingActionButtonDefaults.shape,
                containerColor = Color.Transparent,
                contentColor = Color.White,
                elevation = FloatingActionButtonDefaults.elevation(5.dp),
                ) {
                Icon(
                    painter = painterResource(Res.drawable.add_circle_24dp_e3e3e3),
                    contentDescription = "Create Channel"
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        content = { padding ->
            {Modifier.padding(5.dp)}
                ChatListContent(
                    modifier = Modifier.padding(padding),
                    chatListViewModel = chatListViewModel,
                    onNavigateToMessage,
                )
        }
    )
}

@Composable
fun TopBar(
    onSearch: () -> Unit,
    onAddFriend: () -> Unit,
    onNavigateToLogin: () -> Unit
) {
    Column(
        modifier = Modifier
            .background(Color.Black)
            .fillMaxWidth()
    ) {
        Row {
            Text(
                "Messages",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Left,
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
                    .weight(1f)
            )
            IconButton(
                onClick = {
                    onNavigateToLogin()
                },
                content = {
                    Icon(
                        painter = painterResource(Res.drawable.logout_24dp_e3e3e3_fill0_wght400_grad0_opsz24),
                        contentDescription = "Logout",
                        tint = Color.White
                    )
                }
            )
        }
        Row(
            modifier = Modifier
                .padding(5.dp)
        ) {
            IconButton(
                modifier = Modifier
                    .padding(5.dp)
                    .background(color = Color(red = 0.3f, green = 0.3f, blue = 0.3f), shape = CircleShape),
                onClick = {onSearch()}
            ) {
                Icon(
                    painter = painterResource(Res.drawable.search_24dp_e3e3e3_fill0_wght400_grad0_opsz24),
                    contentDescription = "Search",
                    tint = Color.White,
                    modifier = Modifier
                        .background(color = Color(red = 0.3f, green = 0.3f, blue = 0.3f), shape = CircleShape)
                )
            }
            IconButton(
                onClick = {onAddFriend()},
                modifier = Modifier
                    .padding(5.dp)
                    .weight(1f)
                    .align(Alignment.CenterVertically)
                    .background(color = Color(red = 0.3f, green = 0.3f, blue = 0.3f), shape = CircleShape),
                shape = RoundedCornerShape(50)
            ) {
                Row {
                    Icon(
                        painter = painterResource(Res.drawable.person_add_24dp_e3e3e3_fill0_wght400_grad0_opsz24),
                        contentDescription = "Add Friend",
                        tint = Color.White,
                        modifier = Modifier
                            .align(Alignment.CenterVertically),
                    )
                    Text("Add Friends")
                }
            }
        }
    }
}
@Composable
fun ChatListContent(
    modifier: Modifier,
    chatListViewModel: ChatListViewModel,
    onNavigateToMessage: () -> Unit,
) {
    val channelInfo by chatListViewModel.channelInfo.collectAsStateWithLifecycle()
    LazyColumn(
        modifier = Modifier.then(modifier)
            .fillMaxWidth()
    ) {
        items(1) {
            LazyRow {
                items(1) {
                    ActivityCard(
                        image = null,
                        onlineStatus = "do not disturb",
                        channelName = null,
                        activity = null,
                        note = null
                    )
                }
                items(1) {
                    ActivityCard(
                        image = null,
                        onlineStatus = "do not disturb",
                        channelName = "Channel Name",
                        activity = null,
                        note = null
                    )
                }
            }
        }
        items(channelInfo.toList()) { channel ->
            ListCard(
                image = null,
                channelName = channel.channelName,
                lastMessage = channel.lastMessage,
                onClick = {
                    chatListViewModel.setCurrentChannel(channel.channelID)
                    onNavigateToMessage()
                }
            )
        }
    }
}
@Composable
fun ListCard(
    image: ByteArray?,
    channelName: String,
    lastMessage: String?,
    onClick: () -> Unit,
) {
    var image by remember {mutableStateOf(image)}
    if(image == null) image = imageResource(Res.drawable.download).toByteArray()
    Row (
        modifier = Modifier
            .padding(10.dp)
            .clickable(
                onClick =  { onClick() }
            )
            .fillMaxWidth()
    ) {
        Image(
            bitmap = image!!.toImageBitmap(),
            contentDescription = "Channel Picture",
            modifier = Modifier
                .size(50.dp)
                .clip(shape = CircleShape)

        )
        Column {
            Text(channelName, maxLines = 1)
            lastMessage?.let { Text(it, maxLines = 1) }
        }
    }
}
@Composable
fun ActivityCard(
    image: ByteArray?,
    onlineStatus: String,
    channelName: String?,
    activity: String?,
    note: String?
) {
    var image by remember {mutableStateOf(image)}
    if(image == null) image = imageResource(Res.drawable.download).toByteArray()
    Row(
        modifier = Modifier
            .padding(5.dp)
    ) {
        Image(
            bitmap = image!!.toImageBitmap(),
            contentDescription = "Channel Picture",
            modifier = Modifier
                .size(50.dp)
                .clip(shape = CircleShape)
        )
        if(onlineStatus == "do not disturb") Icon(
            painter = painterResource(Res.drawable.do_not_disturb_on_24dp_e3e3e3_fill0_wght400_grad0_opsz24),
            contentDescription = "Do Not Disturb",
            modifier = Modifier
                .align(Alignment.Bottom)
        )
//        else if(onlineStatus == "offline") Icon()
//        else if(onlineStatus == "online") Icon()
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if(channelName != null) Text(channelName, maxLines = 1)
            if(activity != null) Text(activity, maxLines = 1)
            if(note != null) Text(note, maxLines = 1)
        }
    }
}

@Composable
fun BottomBar(
    chatListViewModel: ChatListViewModel,
    onClickHome: () -> Unit,
    onClickNotifications: () -> Unit,
    onClickProfile: () -> Unit,
) {
       Row(
           modifier = Modifier
               .background(Color.Black)
               .fillMaxWidth()
       ) {
           Box(
               modifier = Modifier
                   .weight(1f)
                   .clickable(
                       enabled = true,
                       onClick = {
                           onClickHome()
                       }
                   ),
               contentAlignment = Alignment.Center
           ) {
               Column {
                   Icon(
                       painter = painterResource(Res.drawable.home_work_24dp_e3e3e3),
                       contentDescription = "Add Friend",
                       tint = Color.White,
                       modifier = Modifier
                           .align(Alignment.CenterHorizontally)
                   )
                   Text("Home", modifier = Modifier.align(Alignment.CenterHorizontally))
               }
           }
           Box(
               modifier = Modifier
                   .weight(1f)
                   .clickable(
                       enabled = true,
                       onClick = {
                           onClickNotifications()
                       }
                   ),
               contentAlignment = Alignment.Center
           ) {
               Column {
                   Icon(
                       painter = painterResource(Res.drawable.notifications_24dp_e3e3e3),
                       contentDescription = "Add Friend",
                       tint = Color.White,
                       modifier = Modifier
                           .align(Alignment.CenterHorizontally)
                   )
                   Text(
                       "Notifications",
                       modifier = Modifier.align(Alignment.CenterHorizontally)
                   )
               }
           }
           Box(
               modifier = Modifier
                   .weight(1f)
                   .clickable(
                       enabled = true,
                       onClick = {
                           onClickProfile()
                       }
                   ),
               contentAlignment = Alignment.Center
           ) {
               Column(

               ) {
                   Image(
                       bitmap = if(chatListViewModel.profilePic.value.contentEquals(ByteArray(0)) || chatListViewModel.profilePic.value == null)
                           imageResource(Res.drawable.download)
                                else
                                    chatListViewModel.profilePic.value!!.toImageBitmap(),
                       contentDescription = "Profile Picture",
                       modifier = Modifier
                           .size(20.dp)
                           .clip(shape = CircleShape)
                           .align(Alignment.CenterHorizontally)
                   )
                   Text("You", modifier = Modifier.align(Alignment.CenterHorizontally))
               }

           }
       }
}