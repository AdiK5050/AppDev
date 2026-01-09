package com.adi_chat.app.ui.app.chat

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.adi_chat.app.shared.composables.Image_With_Status
import com.adi_chat.app.ui.theme.AppTheme
import io.adik5050.discord_like.shared.composables.OnlineStatus
import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.attachment
import kotlinproject.composeapp.generated.resources.call
import kotlinproject.composeapp.generated.resources.pin
import kotlinproject.composeapp.generated.resources.send
import kotlinproject.composeapp.generated.resources.smiley
import kotlinproject.composeapp.generated.resources.video
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun Chat(
    modifier: Modifier = Modifier
) {
    Surface (
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface
    ) {
        Column (
            modifier = Modifier.padding(8.dp)
        ) {
            ChatTopBar()
            MessageContent(
                modifier = Modifier
                    .weight(1f)
            )
            MessageTextField()
        }
    }
}

@Composable
fun ChatTopBar (
    modifier: Modifier = Modifier,
    channelName: String = "BFF team",
    onClickCall: () -> Unit = {},
    onCLickVideo: () -> Unit = {},
    onClickPin: () -> Unit = {},
) {
    Row (
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = channelName,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.ExtraBold
        )
        Row {
            IconButton(
                modifier = Modifier
                    .clip(CircleShape),
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainer,
                    contentColor = MaterialTheme.colorScheme.onSurface
                ),
                onClick = onClickCall,
                content = {
                    Icon(
                        painter = painterResource(Res.drawable.call),
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
                onClick = onCLickVideo,
                content = {
                    Icon(
                        painter = painterResource(Res.drawable.video),
                        contentDescription = "Video",
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
                onClick = onClickPin,
                content = {
                    Icon(
                        painter = painterResource(Res.drawable.pin),
                        contentDescription = "Pin",
                    )
                }
            )
        }
    }
}

@Composable
fun MessageContent (
    modifier: Modifier = Modifier
) {
    Surface (
        modifier = modifier
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(5) {
                MessageCard(
                    image = null,
                    status = OnlineStatus.ONLINE,
                    name = "Richard",
                    time = "6:25",
                    message = "I don't have any sample data and it sucks!"
                )
            }
            items(5) {
                MessageCard(
                    image = null,
                    status = OnlineStatus.DO_NOT_DISTURB,
                    name = "Rebecca",
                    time = "7:43",
                    message = "What Gives, I'll have it set up soon!"
                )
            }
            items(5) {
                MessageCard(
                    image = null,
                    status = OnlineStatus.INVISIBLE,
                    name = "Roberto",
                    time = "4:38",
                    message = "Guess what? The slacker thinks he is in control."
                )
            }
        }
    }
}
@Composable
fun MessageCard (
    modifier: Modifier = Modifier,
    image: ByteArray? = null,
    status: OnlineStatus,
    name: String,
    time: String,
    message: String,
) {
    Row (
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Image_With_Status(
            image = image,
            clickable = false,
            status = status,
            onClick = {},
        )
        Column (
            verticalArrangement = Arrangement.Center
        ){
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.titleMedium,
                )
                Text(
                    text = time,
                    style = MaterialTheme.typography.labelSmall
                )
            }
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }

}

@Composable
fun MessageTextField(
    modifier: Modifier = Modifier,
    onClickAttachment: () -> Unit = {},
    onClickSmiley: () -> Unit = {},
    onCLickSend: () -> Unit = {},
) {
    var message by remember { mutableStateOf(TextFieldValue("")) }
    
    TextField(
        modifier = modifier
            .fillMaxWidth(),
        shape = MaterialTheme.shapes.small,
        value = message,
        onValueChange = { newValue ->
            message = newValue
        },
        placeholder = {
            Text(
                text = "Write a message...",
                style = MaterialTheme.typography.bodyMedium
            )
        },
        leadingIcon = {
            Icon(
                painter = painterResource(Res.drawable.attachment),
                contentDescription = "Attachment",
                modifier = Modifier.clickable(
                    onClick = onClickAttachment
                )
            )
        },
        trailingIcon = {
            Row {
                Icon(
                    painter = painterResource(Res.drawable.smiley),
                    contentDescription = "Smiley",
                    modifier = Modifier.clickable(
                        onClick = onClickSmiley
                    )
                )
                Spacer(modifier = Modifier.size(8.dp))
                Icon(
                    painter = painterResource(Res.drawable.send),
                    contentDescription = "Send",
                    modifier = Modifier.clickable(
                        onClick = onCLickSend
                    )
                )


            }
        }
    )
}

@Preview
@Composable
fun PreviewChat() {
    AppTheme (
        darkTheme = true
    ){
        Surface {
            Chat()
        }
    }
}