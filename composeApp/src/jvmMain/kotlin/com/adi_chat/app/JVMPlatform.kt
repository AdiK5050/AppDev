package com.adi_chat.app

class JVMPlatform : Platform {
    override val name: String = "JVM"
}

actual fun getPlatform(): Platform = JVMPlatform()