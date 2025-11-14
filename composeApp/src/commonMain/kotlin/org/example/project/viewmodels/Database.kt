package org.example.project.viewmodels


import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.ImageBitmap
import kotlinx.serialization.Serializable
import org.example.project.pages.NewLogin
import org.example.project.junk.SampleData
import org.example.project.pages.MessagePage

@Serializable
data class User(val name: String, val password: String)

data class Message(val author: String, val body: String, var profilePic: ImageBitmap? = null, val action: String? = null)


class Database() {

    val users = mutableListOf<User>()
    val messageHistory = mutableStateListOf<Message>()

    var profilePicInDB = mutableStateOf(ImageBitmap(1,1))
    private val settings = AppSetting.settings

    companion object {
        private const val KEY_USERNAME = "user_username"
        private const val KEY_PASSWORD = "user_password"
        private const val KEY_LOGGED_IN = "user_logged_in"
    }


    private fun saveUserSession(username: String, password: String) {
        settings.putString(KEY_USERNAME, username)
        settings.putString(KEY_PASSWORD, password)
        settings.putBoolean(KEY_LOGGED_IN, true)
    }

    fun getStartDestination(): Any {
        if (settings.getBoolean(KEY_LOGGED_IN, defaultValue = false)) return MessagePage
        return NewLogin
        }
    fun getUsername(): String {
        return settings.getString(KEY_USERNAME, defaultValue = "")
    }

    fun getPassword(): String {
        return settings.getString(KEY_PASSWORD, defaultValue = "")
    }

    fun clearUserSession() {
        settings.remove(KEY_USERNAME)
        settings.remove(KEY_PASSWORD)
        settings.putBoolean(KEY_LOGGED_IN, false)
        users.clear()
    }

    fun isLoggedIn(): Boolean {
        return settings.getBoolean(KEY_LOGGED_IN, defaultValue = false)
    }

    fun initUserLoginInfo() {
        users.add(User(getUsername(), getPassword()))
    }

    fun initMessageHistory() {
        val action = "sender"
        for(message in SampleData.conversationSample) {
            val newMessage = Message(message.author, message.body, action = action)
            messageHistory.add(newMessage)
        }
    }
    fun setProfilePic(image: ImageBitmap) {
        profilePicInDB.value = image
        messageHistory.forEach { message -> message.profilePic = profilePicInDB.value }
    }

    fun getProfilePic(): ImageBitmap {
        return profilePicInDB.value
    }

    fun addUser(user: User) {
        saveUserSession(user.name, user.password)
        users.add(user)
    }
}
