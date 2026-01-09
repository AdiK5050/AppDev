package com.adi_chat.app.ui.app.profile.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.adi_chat.app.shared.composables.Image_With_Status
import com.adi_chat.app.ui.theme.AppTheme
import io.adik5050.discord_like.shared.composables.OnlineStatus
import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.add_circle_24dp_e3e3e3
import kotlinproject.composeapp.generated.resources.direct_down
import kotlinproject.composeapp.generated.resources.edit
import kotlinproject.composeapp.generated.resources.edit_profile
import kotlinproject.composeapp.generated.resources.user_thoughts
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ProfileInfo(
    modifier: Modifier = Modifier,
    name: String,
    userId: String,
    pronouns: String,
    image: ByteArray?,
    clickableImage: Boolean,
    onClickImage: () -> Unit,
    status: OnlineStatus,
    onEdit: () -> Unit,
    thoughts: String? = null,
    onClickNotes: () -> Unit = {}
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        ProfileInfoImageRow(
            modifier,
            image = image,
            status = status,
            thoughts = thoughts,
            clickableImage = clickableImage,
            onClickImage = onClickImage,
            onClickNotes = onClickNotes
        )
        ProfileInfoTextRow(
            modifier,
            name = name,
            userId = userId,
            pronouns = pronouns,
        )
        ProfileEditButton(
            modifier = modifier,
            onEdit = onEdit
        )
    }
}
@Composable
fun ProfileInfoImageRow(
    modifier: Modifier = Modifier,
    image: ByteArray? = null,
    status: OnlineStatus = OnlineStatus.ONLINE,
    thoughts: String?,
    clickableImage: Boolean,
    onClickImage: () -> Unit,
    onClickNotes: () -> Unit,
) {
    Row (
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Image_With_Status(
            modifier = Modifier.size(82.dp),
            image = image,
            status = status,
            clickable = clickableImage,
            onClick = onClickImage
        )
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainer,
                contentColor = MaterialTheme.colorScheme.onSurface
            ),
            onClick = onClickNotes,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .heightIn(30.dp)
                    .padding(4.dp)
                    .alpha(0.7f),
            ) {
                Icon(
                    painter = painterResource(Res.drawable.add_circle_24dp_e3e3e3),
                    contentDescription = "Add Circle",
                )
                Text(
                    text = thoughts ?: stringResource(Res.string.user_thoughts),
                    style = MaterialTheme.typography.bodySmall,
                    fontStyle = FontStyle.Italic,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}


@Composable
fun ProfileInfoTextRow(
    modifier: Modifier = Modifier,
    name: String,
    userId: String,
    pronouns: String?,
    onClickName: () -> Unit = {}
) {
    Column(
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clickable(
                    onClick = onClickName
                )
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
            )
            Icon(
                painterResource(Res.drawable.direct_down),
                contentDescription = "Directing down"
            )
        }
        Row (
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = userId,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.size(4.dp))
            Text(
                text = "•"
            )
            Spacer(modifier = Modifier.size(4.dp))
            Text(
                text = pronouns ?: "He/Him",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.size(4.dp))
        }
    }
}

@Composable
fun ProfileEditButton(
    modifier: Modifier = Modifier,
    onEdit: () -> Unit,
) {
    Button(
        modifier = modifier
            .heightIn(min = 40.dp)
            .fillMaxWidth(),
        onClick = onEdit,
        colors = ButtonDefaults.textButtonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
        ),
        content = {
            Icon(
                painterResource(Res.drawable.edit),
                contentDescription = "Edit",
            )
            Text(
                text = stringResource(Res.string.edit_profile),
                style = MaterialTheme.typography.labelLarge
            )
        }
    )
}


@Preview
@Composable
fun PreviewProfileInfo() {
    AppTheme(
        darkTheme = true
    ) {
        Surface (
            modifier = Modifier.padding(top = 24.dp)
        ){
            ProfileInfo(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                name = "Adi",
                userId = "adi8299",
                pronouns = "He/Him",
                image = null,
                status = OnlineStatus.DO_NOT_DISTURB,
                clickableImage = false,
                onClickImage = {},
                onEdit = {},
                thoughts = "Your Favorite Anime?",
                onClickNotes = {},
            )
        }
    }
}