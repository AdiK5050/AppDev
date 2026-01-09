package com.adi_chat.app.ui.app.chat.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.attachment
import kotlinproject.composeapp.generated.resources.send
import kotlinproject.composeapp.generated.resources.smiley
import org.jetbrains.compose.resources.painterResource

@Composable
fun MessageTextField(
    modifier: Modifier = Modifier,
    onClickAttachment: () -> Unit = {},
    onClickSmiley: () -> Unit = {},
    onCLickSend: () -> Unit = {},
) {
    var message by remember { mutableStateOf(TextFieldValue("")) }

    TextField(
        modifier = modifier
            .fillMaxWidth(),
        shape = MaterialTheme.shapes.small,
        value = message,
        onValueChange = { newValue ->
            message = newValue
        },
        placeholder = {
            Text(
                text = "Write a message...",
                style = MaterialTheme.typography.bodyMedium
            )
        },
        leadingIcon = {
            Icon(
                painter = painterResource(Res.drawable.attachment),
                contentDescription = "Attachment",
                modifier = Modifier.clickable(
                    onClick = onClickAttachment
                )
            )
        },
        trailingIcon = {
            Row {
                Icon(
                    painter = painterResource(Res.drawable.smiley),
                    contentDescription = "Smiley",
                    modifier = Modifier.clickable(
                        onClick = onClickSmiley
                    )
                )
                Spacer(modifier = Modifier.size(8.dp))
                Icon(
                    painter = painterResource(Res.drawable.send),
                    contentDescription = "Send",
                    modifier = Modifier.clickable(
                        onClick = onCLickSend
                    )
                )


            }
        }
    )
}