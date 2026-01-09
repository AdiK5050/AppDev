package com.adi_chat.app.chat_page.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.adi_chat.app.ui.theme.AppTheme
import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.compose_multiplatform
import kotlinproject.composeapp.generated.resources.discord_logo
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ServerList(
    modifier: Modifier = Modifier
) {
    LazyColumn (
        modifier = modifier,
    ) {
        item {
            MessageToggle(
                onClick = {}
            )
            Spacer(modifier = Modifier.size(16.dp))
        }
        items(count = 15) {
            ServerIcon()
        }
    }
}

@Composable
fun MessageToggle(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card (
        modifier = modifier
            .padding(8.dp)
            .clickable(
                onClick = onClick
            ),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        )
    ) {
        Icon(
            painterResource(Res.drawable.discord_logo),
            contentDescription = "Server Icon",
            modifier = Modifier
                .padding(8.dp)
                .size(48.dp)
        )
    }
}

@Composable
fun ServerIcon(
    modifier: Modifier = Modifier
) {
    Card (
        modifier = modifier
            .padding(8.dp),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        )
    ) {
        Icon(
            painterResource(Res.drawable.compose_multiplatform),
            contentDescription = "Server Icon",
            modifier = Modifier
                .padding(8.dp)
                .size(48.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewServerListLight() {
    AppTheme {
        Surface {
            ServerList()
        }
    }
}
@Preview(showBackground = true)
@Composable
fun PreviewServerListDark() {
    AppTheme (
        darkTheme = true
    ) {
        Surface {
            ServerList()
        }
    }
}
