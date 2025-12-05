package com.adi_chat.app

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform