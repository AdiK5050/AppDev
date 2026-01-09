package com.adi_chat.app.chat_page.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.adi_chat.app.shared.composables.Image_With_Status
import com.adi_chat.app.ui.theme.AppTheme
import io.adik5050.discord_like.shared.composables.ImageCard
import io.adik5050.discord_like.shared.composables.OnlineStatus
import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.files_hollow
import kotlinproject.composeapp.generated.resources.key_board_arrow_down
import kotlinproject.composeapp.generated.resources.key_board_arrow_up
import kotlinproject.composeapp.generated.resources.link_hollow
import kotlinproject.composeapp.generated.resources.notifications_hollow
import kotlinproject.composeapp.generated.resources.photo_library_hollow
import kotlinproject.composeapp.generated.resources.settings_hollow
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ChatInfo(
    modifier: Modifier = Modifier
) {
    Surface (
        modifier = modifier
            .fillMaxSize(),
    ) {
        Column (
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            ChatInfoTopBar()
            MembersInfo(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.75f)
            )
            MediaInfo(
                modifier = Modifier
                    .weight(0.5f)
            )
        }
    }
}

@Composable
fun ChatInfoTopBar(
    modifier: Modifier = Modifier,
    name: String = "Mrs. Jennifer Lawrence",
    image: ByteArray? = null,
    status: OnlineStatus = OnlineStatus.ONLINE,
    onClickSettings: () -> Unit = {},
    onClickNotifications: () -> Unit = {},
    onClickProfile: () -> Unit = {}
) {
    Row (
        modifier = modifier
            .padding(top = 8.dp, bottom = 8.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        Row {
            IconButton(
                modifier = Modifier
                    .clip(CircleShape),
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainer,
                    contentColor = MaterialTheme.colorScheme.onSurface
                ),
                onClick = onClickSettings,
                content = {
                    Icon(
                        painter = painterResource(Res.drawable.settings_hollow),
                        contentDescription = "Call",
                    )
                }
            )
            IconButton(
                modifier = Modifier
                    .clip(CircleShape),
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainer,
                    contentColor = MaterialTheme.colorScheme.onSurface
                ),
                onClick = onClickNotifications,
                content = {
                    Icon(
                        painter = painterResource(Res.drawable.notifications_hollow),
                        contentDescription = "Call",
                    )
                }
            )
        }
        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ){
            Text(
                modifier = Modifier.weight(1f),
                text = name,
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.End,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis

            )
            Image_With_Status(
                image = image,
                status = status,
                clickable = true,
                onClick = onClickProfile
            )
        }
    }
}

@Composable
fun MembersInfo (
    modifier: Modifier = Modifier,
    onClickAddNew: () -> Unit = {}
) {
    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row (
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(
                text = "Members",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
            )
            Text(
                modifier = Modifier
                    .clickable(
                        onClick = onClickAddNew
                    ),
                text = "Add New",
                style = MaterialTheme.typography.titleMedium
            )
        }
        LazyColumn (
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ){
            item {
                MembersCard(
                    name = "Richard Wilson",
                    image = null,
                    status = OnlineStatus.ONLINE,
                    about = "playing NFS Heat",
                    activity = "",
                    role = "Admin",
                    onClickProfile = {}
                )
            }
            items(5) {
                MembersCard(
                    name = "Jenna Oritz",
                    image = null,
                    status = OnlineStatus.ONLINE,
                    about = "",
                    activity = "",
                    role = "",
                    onClickProfile = {}
                )
            }
            items(5) {
                MembersCard(
                    name = "Jenna Oritz",
                    image = null,
                    status = OnlineStatus.DO_NOT_DISTURB,
                    about = "",
                    activity = "",
                    role = "",
                    onClickProfile = {}
                )
            }
        }
    }
}
@Composable
fun MembersCard(
    modifier: Modifier = Modifier,
    name: String,
    image: ByteArray?,
    status: OnlineStatus,
    about: String,
    activity: String,
    role: String,
    onClickProfile: () -> Unit
) {
    Row (
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image_With_Status(
                image = image,
                status = status,
                clickable = true,
                onClick = onClickProfile
            )
            Column (
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.titleMedium
                )
                if(about.isNotBlank())
                    Text(
                        text = about,
                        style = MaterialTheme.typography.titleSmall
                    )
                if(activity.isNotBlank())
                    Text(
                        text = activity,
                        style = MaterialTheme.typography.titleSmall
                    )
            }
        }
        if(role.isNotBlank())
            Text(
                role,
                style = MaterialTheme.typography.titleMedium
            )
    }
}

@Composable
fun MediaInfo(
    modifier: Modifier = Modifier,
    onClickViewAll: () -> Unit = {}
) {
    Column (
        modifier = modifier
    ) {
        Row (
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(
                text = "Files",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Text(
                modifier = Modifier
                    .clickable(
                        onClick = onClickViewAll
                    ),
                text = "View All",
                style = MaterialTheme.typography.titleMedium
            )
        }
        LazyColumn {
            item {
                Media(
                    contentIcon = Res.drawable.photo_library_hollow,
                    contentType = SharedContentType.Photos,
                    numberOfSharedContent = 295,
                )
                Media(
                    contentIcon = Res.drawable.files_hollow,
                    contentType = SharedContentType.Files,
                    numberOfSharedContent = 378,
                )
                Media(
                    contentIcon = Res.drawable.link_hollow,
                    contentType = SharedContentType.Links,
                    numberOfSharedContent = 45,
                )
            }
        }
    }
}

@Composable
fun Media(
    modifier: Modifier = Modifier,
    contentIcon: DrawableResource,
    contentType: SharedContentType,
    numberOfSharedContent: Int,
) {
    var expanded by remember { mutableStateOf(false) }
    Column (
        modifier = modifier
    ) {
        Row (
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Row (
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    painter = painterResource(contentIcon as DrawableResource),
                    contentDescription = "Photos"
                )
                Text("$numberOfSharedContent $contentType")
            }
            IconButton(
                onClick = {
                    expanded = !expanded
                },
                content = {
                    if(expanded)
                        Icon(
                            painter = painterResource(Res.drawable.key_board_arrow_down),
                            contentDescription = "Expand"
                        )
                    else
                        Icon(
                            painter = painterResource(Res.drawable.key_board_arrow_up),
                            contentDescription = "Expand"
                        )
                }
            )
        }
        if(expanded)
            LazyRow (
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(count = 5) {
                    ImageCard(
                        modifier
                            .heightIn(max = 100.dp)
                            .widthIn(max = 200.dp),
                        image = null
                    )
                }
            }
    }
}
@Composable
fun MediaType_Files(
    modifier: Modifier = Modifier,
    numberOfFiles: Int = 378,
) {
    var expanded by remember { mutableStateOf(false) }
    Column {
        Row (
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row (
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    painter = painterResource(Res.drawable.files_hollow),
                    contentDescription = "Files"
                )

                Text(
                    text = "$numberOfFiles files"
                )
            }
            IconButton(
                onClick = {
                    expanded = !expanded
                },
                content = {
                    if(expanded)
                        Icon(
                            painter = painterResource(Res.drawable.key_board_arrow_down),
                            contentDescription = "Expand"
                        )
                    else
                        Icon(
                            painter = painterResource(Res.drawable.key_board_arrow_up),
                            contentDescription = "Expand"
                        )
                }
            )
        }
    }
}

@Preview
@Composable
fun PreviewChatInfo() {
    AppTheme {
        Surface {
            ChatInfo()
        }
    }
}
@Preview
@Composable
fun PreviewChatInfoDark() {
    AppTheme (darkTheme = true){
        Surface {
            ChatInfo()
        }
    }
}
