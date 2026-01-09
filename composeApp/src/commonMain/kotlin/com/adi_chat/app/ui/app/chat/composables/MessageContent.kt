package com.adi_chat.app.ui.app.chat.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.adi_chat.app.chat_page.composables.MessageCard
import io.adik5050.discord_like.shared.composables.OnlineStatus

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