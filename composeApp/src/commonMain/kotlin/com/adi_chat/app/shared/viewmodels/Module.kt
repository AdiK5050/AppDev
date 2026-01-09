package com.adi_chat.app.shared.viewmodels

import com.russhwolf.settings.Settings
import kotlinx.serialization.Serializable
@Serializable
object AppSetting {
    val settings: Settings = Settings()
}