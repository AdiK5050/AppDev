package org.example.project.viewmodels


import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.graphics.ImageBitmap
import kotlinx.serialization.Serializable
import org.example.project.MessagePage
import org.example.project.NewLogin
import org.example.project.Junk.SampleData
@Serializable
data class User(val name: String, val password: String)

data class Message(val author: String, val body: String, var profilePic: ImageBitmap? = null, val action: String? = null)


class Database() {

    val users = mutableListOf<User>()
    val messageHistory = mutableStateListOf<Message>()

    var profilePicInDB = ImageBitmap(1,1)
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
        profilePicInDB = image
    }

    fun getProfilePic(): ImageBitmap {
        return profilePicInDB
    }

    fun addUser(user: User) {
        saveUserSession(user.name, user.password)
        users.add(user)
    }
}
