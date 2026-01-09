package com.adi_chat.app.ui.app.profile

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.adi_chat.app.shared.composables.BottomNavigation
import com.adi_chat.app.ui.app.profile.composables.ContentCards
import com.adi_chat.app.ui.app.profile.composables.ProfileInfo
import com.adi_chat.app.ui.app.profile.composables.TopOptions
import com.adi_chat.app.ui.theme.AppTheme
import io.adik5050.discord_like.shared.composables.OnlineStatus
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun PersonalProfilePage(

) {
    Surface (
        modifier = Modifier
            .fillMaxSize(),
        content = {
            Column (
                modifier = Modifier
                    .padding(top = 32.dp)
                    .fillMaxSize()
            ) {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    item {
                        TopOptions(
                            modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                        )
                    }
                    item {
                        ProfileInfo(
                            modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                            name = "Adi",
                            userId = "adi8299",
                            pronouns = "He/Him",
                            image = null,
                            clickableImage = true,
                            onClickImage = {},
                            status = OnlineStatus.ONLINE,
                            onEdit = {},
                            thoughts = "What's new in Christmas?",
                            onClickNotes = {},
                        )
                    }
                    item {
                        ContentCards(
                            modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                            connections = true,
                            orbs = 1080,
                            about = "Change Is Fated",
                            memberSince = "Jul 9, 2024"
                        )
                    }
                    item {
                        ContentCards(
                            modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                            connections = true,
                            orbs = 1080,
                            about = "Change Is Fated",
                            memberSince = "Jul 9, 2024"
                        )
                    }
                }
                BottomNavigation(
                    modifier = Modifier
                        .padding(top = 8.dp, bottom = 0.dp)
                        .heightIn(max = 72.dp)
                        .align( Alignment.End ),
                    onClickHome = {},
                    onClickNotifications = {},
                    onClickProfile = {}
                )
            }
        }
    )
}
@Preview
@Composable
fun PreviewPersonalProfilePage() {
    AppTheme(
        darkTheme = true
    ) {
        PersonalProfilePage()
    }
}