package com.adi_chat.app.ui.app.profile.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.adi_chat.app.ui.theme.AppTheme
import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.discord_nitro
import kotlinproject.composeapp.generated.resources.discord_quest
import kotlinproject.composeapp.generated.resources.discord_settings
import kotlinproject.composeapp.generated.resources.discord_store
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun TopOptions(
    onQuest: () -> Unit = {},
    onNitro: () -> Unit = {},
    onStore: () -> Unit = {},
    onSettings: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.BottomEnd
    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            IconButton(
                modifier = Modifier.size(40.dp),
                shape = CircleShape,
                onClick = onQuest,
                content = {
                    Icon(
                        painter = painterResource(Res.drawable.discord_quest),
                        contentDescription = "Quest"
                    )
                }
            )
            IconButton(
                modifier = Modifier.size(40.dp),
                shape = CircleShape,
                onClick = onStore,
                content = {
                    Icon(
                        painter = painterResource(Res.drawable.discord_store),
                        contentDescription = "Store"
                    )
                }
            )
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable(
                        onClick = onNitro
                    ),
                contentAlignment = Alignment.Center,
            ) {
                Row (
                    modifier = Modifier.padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.discord_nitro),
                        contentDescription = "Nitro"
                    )
                    Text(
                        text = stringResource(Res.string.discord_nitro),
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
            IconButton(
                modifier = Modifier.size(40.dp),
                shape = CircleShape,
                onClick = onSettings,
                content = {
                    Icon(
                        painter = painterResource(Res.drawable.discord_settings),
                        contentDescription = "Settings"
                    )
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTopOptions() {
    AppTheme(
        darkTheme = false
    ) {
        Surface(
            color = MaterialTheme.colorScheme.surface
        ) {
            TopOptions(
                modifier = Modifier
                    .padding(8.dp)
            )
        }
    }
}