package com.adi_chat.app.ui.app.chat.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.call
import kotlinproject.composeapp.generated.resources.pin
import kotlinproject.composeapp.generated.resources.video
import org.jetbrains.compose.resources.painterResource

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