package com.adi_chat.app.shared.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.adi_chat.app.ui.theme.AppTheme
import com.wannaverse.imageselector.toImageBitmap
import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.bottom_navigation_home
import kotlinproject.composeapp.generated.resources.bottom_navigation_notifications
import kotlinproject.composeapp.generated.resources.bottom_navigation_profile
import kotlinproject.composeapp.generated.resources.discord_home
import kotlinproject.composeapp.generated.resources.discord_logo
import kotlinproject.composeapp.generated.resources.discord_notifications
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun BottomNavigation (
    modifier: Modifier = Modifier,
    image: ByteArray? = null,
    onClickHome: () -> Unit,
    onClickNotifications: () -> Unit,
    onClickProfile: () -> Unit,
) {

    NavigationBar (
        modifier = modifier
    ) {
        NavigationBarItem(
            icon = {
                Icon(
                    painter = painterResource(Res.drawable.discord_home),
                    contentDescription = "Home"
                )
            },
            label = {
                Text(
                    text = stringResource(Res.string.bottom_navigation_home),
                    style = MaterialTheme.typography.labelSmall,
                    fontSize = 10.sp
                    )
            },
            selected = false,
            onClick = {
                onClickHome
            }
        )
        NavigationBarItem(
            icon = {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(Res.drawable.discord_notifications),
                    contentDescription = "Notifications"
                )
            },
            label = {
                Text(
                    text = stringResource(Res.string.bottom_navigation_notifications),
                    style = MaterialTheme.typography.labelSmall,
                    fontSize = 10.sp
                )
            },
            selected = false,
            onClick = {
                onClickNotifications
            }
        )
        NavigationBarItem(
            icon = {
                if(image == null || image.contentEquals(ByteArray   (0)))
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(Res.drawable.discord_logo),
                        contentDescription = "Profile"
                    )
                else
                    Icon(
                        bitmap = image.toImageBitmap(),
                        contentDescription = "Profile"
                    )
            },
            label = {
                Text(
                    text = stringResource(Res.string.bottom_navigation_profile),
                    style = MaterialTheme.typography.labelSmall,
                    fontSize = 10.sp
                )
            },
            selected = false,
            onClick = {
                onClickProfile
            }
        )
    }
}

@Preview
@Composable
fun PreviewBottomNavigation() {
    AppTheme {
        Surface {
            Column {
                BottomNavigation(
                    onClickHome = {},
                    onClickNotifications = {},
                    onClickProfile = {}
                )
            }
        }
    }
}