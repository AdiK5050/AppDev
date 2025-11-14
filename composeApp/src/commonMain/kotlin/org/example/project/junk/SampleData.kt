package org.example.project.junk

/**
 * SampleData for Jetpack Compose Tutorial 
 */
data class OldMessages(val author: String, val body: String)
object SampleData {
    // Sample conversation data
    val conversationSample = listOf(
        OldMessages(
            "Rias",
            "Test...Test...Test..."
        ),
        OldMessages(
            "Rias",
            """List of Android versions:
            |Android KitKat (API 19)
            |Android Lollipop (API 21)
            |Android Marshmallow (API 23)
            |Android Nougat (API 24)
            |Android Oreo (API 26)
            |Android Pie (API 28)
            |Android 10 (API 29)
            |Android 11 (API 30)
            |Android 12 (API 31)""".trim()
        ),
        OldMessages(
            "Rias",
            """I think Kotlin is my favorite programming language.
            |It's so much fun!""".trim()
        ),
        OldMessages(
            "Rias",
            "Searching for alternatives to XML layouts..."
        ),
        OldMessages(
            "Rias",
            """Hey, take a look at Jetpack Compose, it's great!
            |It's the Android's modern toolkit for building native UI.
            |It simplifies and accelerates UI development on Android.
            |Less code, powerful tools, and intuitive Kotlin APIs :)""".trim()
        ),
        OldMessages(
            "Rias",
            "It's available from API 21+ :)"
        ),
        OldMessages(
            "Rias",
            "Writing Kotlin for UI seems so natural, Compose where have you been all my life?"
        ),
        OldMessages(
            "Rias",
            "Android Studio next version's name is Arctic Fox"
        ),
        OldMessages(
            "Rias",
            "Android Studio Arctic Fox tooling for Compose is top notch ^_^"
        ),
        OldMessages(
            "Rias",
            "I didn't know you can now run the emulator directly from Android Studio"
        ),
        OldMessages(
            "Rias",
            "Compose Previews are great to check quickly how a composable layout looks like"
        ),
        OldMessages(
            "Rias",
            "Previews are also interactive after enabling the experimental setting"
        ),
        OldMessages(
            "Rias",
            "Have you tried writing build.gradle with KTS?"
        ),
    ).toMutableList()
}
