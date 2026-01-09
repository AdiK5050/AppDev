package com.adi_chat.app.shared.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.wannaverse.imageselector.toImageBitmap
import io.adik5050.discord_like.shared.composables.OnlineStatus
import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.discord
import kotlinproject.composeapp.generated.resources.status_do_not_disturb
import kotlinproject.composeapp.generated.resources.status_online
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun Image_With_Status(modifier: Modifier = Modifier,
                      image: ByteArray?,
                      status: OnlineStatus = OnlineStatus.NONE,
                      clickable: Boolean = false,
                      onClick: () -> Unit = {}
) {
    Box (
        modifier = modifier
    ) {
        Box (
            modifier = Modifier
                .clip(CircleShape)
                .clickable (
                    clickable,
                    onClick = { onClick() }
                )
        ) {
            if(image == null || image.contentEquals(ByteArray(0)))
                Image(
                    painterResource(Res.drawable.discord),
                    contentDescription = "Profile Picture",
                    contentScale = ContentScale.Crop,
                    modifier = modifier
                )
            else
                Image (
                    bitmap = image.toImageBitmap(),
                    contentDescription = "A photo of a beauty.",
                    contentScale = ContentScale.Crop,
                    modifier = modifier
                )
        }
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .border(width = 4.dp, color = MaterialTheme.colorScheme.background, shape = CircleShape)
                .background(color = MaterialTheme.colorScheme.background,shape = CircleShape)
        ) {
            when (status) {
                OnlineStatus.DO_NOT_DISTURB -> Icon(
                    painterResource(Res.drawable.status_do_not_disturb),
                    contentDescription = "Do Nod Disturb",
                    tint = Color(0xfff02d2d),
                    modifier = Modifier.align(alignment = Alignment.Center)
                )
                OnlineStatus.ONLINE -> Icon(
                    painterResource(Res.drawable.status_online),
                    contentDescription = "Online",
                    tint = Color(0xff00c230),
                    modifier = Modifier.align(alignment = Alignment.Center)
                )
                OnlineStatus.INVISIBLE -> Icon(
                    painterResource(Res.drawable.status_online),
                    contentDescription = "Online",
                    tint = Color.Gray,
                    modifier = Modifier.align(alignment = Alignment.Center)
                )
                else -> {}
            }
        }
    }
}



@Preview
@Composable
fun Preview_Image_With_Status() {
    MaterialTheme(darkColorScheme()) {
        Surface {
            Column {
                Image_With_Status(Modifier.size(70.dp), null, OnlineStatus.ONLINE, false, {})
                Image_With_Status(Modifier.size(70.dp), null, OnlineStatus.DO_NOT_DISTURB, false, {})
                Image_With_Status(Modifier.size(70.dp), null, OnlineStatus.INVISIBLE, false, {})
            }
        }
    }
}