package com.adi_chat.app.chat_page

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.adi_chat.app.platform_specific.WindowSizes
import com.adi_chat.app.ui.theme.AppTheme
import com.adi_chat.app.chat_page.composables.Chat
import com.adi_chat.app.chat_page.composables.ChatInfo
import com.adi_chat.app.chat_page.composables.ChatList
import com.adi_chat.app.chat_page.composables.ServerList
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
fun ChatPage(
    windowSize: WindowSizes,
    modifier: Modifier = Modifier
) {
    Row (
        modifier = modifier
            .fillMaxSize(),
    ) {
        ServerList(
            modifier = Modifier
                .width(80.dp)
        )
        Surface (
            shape = MaterialTheme.shapes.large
        ){
            Row {
                ChatList(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth()
                        .weight(0.30f)
                )
                if(windowSize == WindowSizes.MEDIUM || windowSize == WindowSizes.LARGE) Chat(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                )
                if(windowSize == WindowSizes.LARGE) ChatInfo(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(0.30f)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewChatPage() {
    AppTheme {
        Surface {
            ChatPage(
                windowSize = WindowSizes.COMPACT
            )
        }
    }
}
@Preview(showBackground = true)
@Composable
fun PreviewChatPageDark() {
    AppTheme (
        darkTheme = true
    ) {
        Surface {
            ChatPage(
                windowSize = WindowSizes.COMPACT
            )
        }
    }
}