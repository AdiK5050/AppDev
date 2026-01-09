package com.adi_chat.app.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface Route: NavKey {

    class ChatList: Route
    data class Chat( val channelId: Int ) : Route
}