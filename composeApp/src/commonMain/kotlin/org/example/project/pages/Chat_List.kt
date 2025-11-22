package org.example.project.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.unit.dp
import com.wannaverse.imageselector.toImageBitmap
import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.do_not_disturb_on_24dp_e3e3e3_fill0_wght400_grad0_opsz24
import kotlinproject.composeapp.generated.resources.download
import kotlinproject.composeapp.generated.resources.home_work_24dp_e3e3e3
import kotlinproject.composeapp.generated.resources.notifications_24dp_e3e3e3
import kotlinproject.composeapp.generated.resources.person_add_24dp_e3e3e3_fill0_wght400_grad0_opsz24
import kotlinproject.composeapp.generated.resources.search_24dp_e3e3e3_fill0_wght400_grad0_opsz24
import org.example.project.Destination
import org.example.project.toByteArray
import org.jetbrains.compose.resources.imageResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

object ChatList: Destination

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatList(
//    chatListViewModel: ChatListViewModel,
//    sharedViewModel: SharedViewModel,
//    onNavigateToMessage: () -> Unit,
//    onNavigateToLogin: () -> Unit,
    ) {

    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp)
                .background(color = Color.Black),
        ) {
            Row(
                modifier = Modifier
            ) {
                TopBar(
                    onAddFriend = { /*TODO*/ },
                    onSearch = { /*TODO*/ }
                )
            }
            Column(
                modifier = Modifier
                    .weight(0.5f)
            ) {
                ChatListContent()
            }
            Row(
                modifier = Modifier
            ) {
                BottomAppBar {
                    BottomBar(
                        image = null,
                        onClickHome = { /*TODO*/ },
                        onClickNotifications = { /*TODO*/ },
                        onClickProfile = { /*TODO*/ },
                    )
                }
            }
        }
    }
}

@Composable
fun TopBar(
    onAddFriend: () -> Unit,
    onSearch: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
    ) {
       Text(
           "Messages",
           color = Color.White,
           style = MaterialTheme.typography.headlineSmall,
           modifier = Modifier
               .align(Alignment.Start),
       )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            IconButton(
                modifier = Modifier
                    .padding(10.dp)
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
                    .padding(10.dp)
                    .fillMaxWidth(1f)
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
fun ChatListContent() {
    LazyColumn(
        modifier = Modifier
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
                items(1) {
                    ActivityCard(
                        image = null,
                        onlineStatus = "do not disturb",
                        channelName = "Channel Name",
                        activity = "Playing Rust",
                        note = null
                    )
                }
                items(1) {
                    ActivityCard(
                        image = null,
                        onlineStatus = "online",
                        channelName = "Channel Name",
                        activity = "Playing Rust",
                        note = "Hello Hellow"
                    )
                }
            }
        }
        items(10) {
            ListCard(
                image = null,
                channelName = "Channel Name",
                lastMessage = "Last Message"
            )
        }
    }
}
@Composable
fun ListCard(
    image: ByteArray?,
    channelName: String,
    lastMessage: String,
) {
    var image by remember {mutableStateOf(image)}
    if(image == null) image = imageResource(Res.drawable.download).toByteArray()
    Row (
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
        Column {
            Text(channelName, maxLines = 1)
            Text(lastMessage, maxLines = 1)
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
    image: ByteArray?,
    onClickHome: () -> Unit,
    onClickNotifications: () -> Unit,
    onClickProfile: () -> Unit
) {

        var image: ByteArray? by remember {mutableStateOf(null)}
        if(image == null) image = imageResource(Res.drawable.download).toByteArray()
       Row(
           modifier = Modifier
               .fillMaxWidth()
               .padding(5.dp)
       ) {
           Box(
               modifier = Modifier
                   .fillMaxSize()
                   .weight(1f)
                   .clickable(
                       enabled = true,
                       onClick = {
                           onClickProfile()
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
                   .fillMaxSize()
                   .weight(1f)
                   .clickable(
                       enabled = true,
                       onClick = {
                           onClickProfile()
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
                   .fillMaxSize()
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
                       bitmap = image!!.toImageBitmap(),
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

@Preview
@Composable
fun PreviewChatList() {

    MaterialTheme(
        colorScheme = darkColorScheme(),
    ) {
        Surface(modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .displayCutoutPadding()
            ,
            color = MaterialTheme.colorScheme.secondaryContainer,
        ) {
            Column {
                ChatList()
            }
        }
    }
}