package com.adi_chat.app.ui.app.profile.composables

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.unit.dp
import com.adi_chat.app.ui.theme.AppTheme
import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.about_me
import kotlinproject.composeapp.generated.resources.connections
import kotlinproject.composeapp.generated.resources.discord_logo
import kotlinproject.composeapp.generated.resources.friends
import kotlinproject.composeapp.generated.resources.github_logo
import kotlinproject.composeapp.generated.resources.member_since
import kotlinproject.composeapp.generated.resources.orb
import kotlinproject.composeapp.generated.resources.orbs
import kotlinproject.composeapp.generated.resources.orbs_balance
import kotlinproject.composeapp.generated.resources.spotify_logo
import kotlinproject.composeapp.generated.resources.verified
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ContentCards (
    modifier: Modifier = Modifier,
    orbs: Int,
    about: String,
    memberSince: String,
    connections: Boolean,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OrbsCard(
            orbs = orbs
        )
        AboutMeCard(
            about = about,
            memberSince = memberSince
        )
        if(connections)
            ConnectionsCard()
        FriendsCard()
    }
}
@Composable
fun OrbsCard(
    modifier: Modifier = Modifier,
    orbs: Int = 0,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
        ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(Res.string.orbs_balance),
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                ),
                content = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            painterResource(Res.drawable.orb),
                            contentDescription = stringResource(Res.string.orbs)
                        )
                        Text(
                            text = orbs.toString(),
                            style = MaterialTheme.typography.titleSmall
                        )
                    }
                }
            )
        }
    }
}

@Composable
fun AboutMeCard(
    modifier: Modifier = Modifier,
    about: String = "Your About",
    memberSince: String = "Jan 1, 2023"
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
        ),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(28.dp),
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text (
                    text = stringResource(Res.string.about_me),
                    style = MaterialTheme.typography.titleSmall
                )
                Text (
                    text = about,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = stringResource(Res.string.member_since),
                    style = MaterialTheme.typography.titleSmall
                )
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ){
                    Icon(
                        modifier = Modifier.size(16.dp),
                        painter = painterResource(Res.drawable.discord_logo),
                        contentDescription = "Discord Logo",
                    )
                    Text(
                        text = memberSince,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
        }
    }
}

@Composable
fun ConnectionsCard(
    modifier: Modifier = Modifier
) {
    Card (
        modifier = modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
        ),
    ) {
        Column (
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = stringResource(Res.string.connections),
                style = MaterialTheme.typography.titleSmall
            )
            Spacer(modifier = Modifier.size(16.dp))
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.github_logo),
                        contentDescription = "Github Logo",
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.size(20.dp))
                    Text(
                        text = "AdiK5050",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.size(5.dp))
                    Icon(
                        modifier = Modifier.size(16.dp),
                        painter = painterResource(Res.drawable.verified),
                        contentDescription = "Verified"
                    )
                }
                HorizontalDivider(modifier = Modifier
                    .padding(start = 40.dp) ,thickness = 1.dp)
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.spotify_logo),
                        contentDescription = "Spotify Logo",
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.size(20.dp))
                    Text(
                        text = "Aditya",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.size(5.dp))
                    Icon(
                        modifier = Modifier.size(16.dp),
                        painter = painterResource(Res.drawable.verified),
                        contentDescription = "Verified"
                    )
                }
            }
        }
    }
}

@Composable
fun FriendsCard(
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
        ),
    ) {
        Row (
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = stringResource(Res.string.friends),
                style = MaterialTheme.typography.titleSmall
            )
        }
    }
}

@Preview
@Composable
fun PreviewContentCards() {
    AppTheme(
        darkTheme = true
    ) {
        Surface {
            ContentCards(
                orbs = 0,
                about = "Change Is Fated",
                memberSince = "Jul 9, 2023",
                connections = true,
            )
        }
    }
}