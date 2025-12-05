package com.adi_chat.app.shared_viewmodels

import com.russhwolf.settings.Settings
import kotlinx.serialization.Serializable
@Serializable
object AppSetting {
    val settings: Settings = Settings()
}