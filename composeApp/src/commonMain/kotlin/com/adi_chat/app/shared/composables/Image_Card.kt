package io.adik5050.discord_like.shared.composables

import androidx.compose.foundation.Image
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.adi_chat.app.ui.theme.AppTheme
import com.wannaverse.imageselector.toImageBitmap
import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.compose_multiplatform
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ImageCard(
    modifier: Modifier = Modifier,
    image: ByteArray?,
) {
    Card (
        modifier = modifier,
        shape = MaterialTheme.shapes.extraLarge
    ) {
        if(image != null)
            Image(
                bitmap = image.toImageBitmap(),
                contentDescription = "Image",
            )
        else
            Image(
                painter = painterResource(Res.drawable.compose_multiplatform),
                contentDescription = "Default Image"
            )
    }
}

@Preview
@Composable
fun PreviewImageCard() {
    AppTheme {
        Surface {
            ImageCard(
                image = null
            )
        }
    }
}
@Preview
@Composable
fun PreviewImageCardDark() {
    AppTheme (
        darkTheme = true
    ) {
        Surface {
            ImageCard(
                image = null
            )
        }
    }
}