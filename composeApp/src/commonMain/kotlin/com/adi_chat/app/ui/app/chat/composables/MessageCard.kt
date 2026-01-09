package com.adi_chat.app.ui.app.chat.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.adi_chat.app.shared.composables.Image_With_Status
import io.adik5050.discord_like.shared.composables.OnlineStatus

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