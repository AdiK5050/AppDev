package com.adi_chat.app

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toAwtImage
import java.io.ByteArrayOutputStream
import javax.imageio.ImageIO

actual fun ImageBitmap.toByteArray(): ByteArray {
    val bufferedImage = this.toAwtImage()
    val outputStream = ByteArrayOutputStream()
    ImageIO.write(bufferedImage, "png", outputStream)
    return outputStream.toByteArray()
}