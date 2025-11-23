package org.example.project.pages

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.serialization.Serializable
import org.example.project.Destination
import org.example.project.storage.AppDatabase
import org.example.project.storage.ChannelEntity
import org.example.project.storage.UserEntity

@Serializable
object Contacts: Destination

@Composable
fun ContactsPage(users: List<UserEntity>,
                 appDatabase: AppDatabase,
                 onNavigateToMessagePage: () -> Unit,
                 ) {
    val channelDao = appDatabase.getChannelDao()
    LazyColumn {
        items(users) { user ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clickable(
                        onClick = {
                            //channelDao.insertChannel(ChannelEntity(channelName = user.username))
                        }
                    )
            ) {
                Text(user.username)
            }
        }
    }
}