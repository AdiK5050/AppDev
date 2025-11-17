package org.example.project.storage


import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.serialization.Serializable
import org.example.project.Destination
import org.example.project.pages.NewLogin
import org.example.project.pages.MessagePage
import org.example.project.viewmodels.AppSetting

@Serializable
data class User( val name: String, val password: String,var profilePic: ByteArray? = null) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        if (name != other.name) return false
        if (password != other.password) return false
        if (!profilePic.contentEquals(other.profilePic)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + password.hashCode()
        result = 31 * result + (profilePic?.contentHashCode() ?: 0)
        return result
    }
}

data class Message(val messageId: Int, val uidFrom: Int, val uidTo: Int, val message: String, val timestamp: Long)


class Database(appDatabase: AppDatabase) {

    //Getting DAOs
     val userDao = appDatabase.getUserDao()
     val messageDao = appDatabase.getMessageDao()

//    val users = mutableListOf<User>()
//    val messageHistory = mutableStateListOf<Message>()

    val userEntity = MutableStateFlow<List<UserEntity>>(emptyList())

    val messageEntity = MutableStateFlow<List<MessageEntity>>(emptyList())

    val uidToProfilePage = mutableStateOf(0)
    var profilePicInDB = mutableStateOf(ByteArray(0))

    private val settings = AppSetting.settings

//    companion object {
//        private const val KEY_USERNAME = "user_username"
//        private const val KEY_PASSWORD = "user_password"
//        private const val KEY_LOGGED_IN = "user_logged_in"
//    }


//    fun getStartDestination(): Destination {
//        if (settings.getBoolean(KEY_LOGGED_IN, defaultValue = false)) return MessagePage
//        return NewLogin
//        }
//
//    fun getUsername(): String {
//        return settings.getString(KEY_USERNAME, defaultValue = "")
//    }
//    fun getPassword(): String {
//        return settings.getString(KEY_PASSWORD, defaultValue = "")
//    }

//    fun initUserLoginInfo() {
//        users.add(User(getUsername(), getPassword(),))
//    }
//
//    fun initMessageHistory() {
//        for(message in SampleData.conversationSample) {
//            val newMessage = Message(message.author, message.body)
//            messageHistory.add(newMessage)
//        }
//    }

//    @OptIn(DelicateCoroutinesApi::class)
//    fun setProfilePic(image: ByteArray, uid: Int) {
//        GlobalScope.async {
//            userDao.setPicByUID(uid, image)
//        }
//        profilePicInDB.value = image
//
//    }
//    @OptIn(DelicateCoroutinesApi::class)
//    fun getProfilePic(uid: Int): ByteArray {
//        GlobalScope.async {
//            profilePicInDB.value = userDao.getPicByUID(uid) as ByteArray
//        }
//        return profilePicInDB.value
//    }
//
//    @OptIn(DelicateCoroutinesApi::class)
//    private fun saveUserSession(username: String, password: String): Boolean {
//        var userEntity: UserEntity? = null
//        GlobalScope.async {
//            userEntity = userDao.getByName(username)
//        }
//        if(userEntity != null) return true
//        else {
//            settings.putString(KEY_USERNAME, username)
//            settings.putString(KEY_PASSWORD, password)
//            settings.putBoolean(KEY_LOGGED_IN, true)
//            return false
//        }
//    }
//
//    @OptIn(DelicateCoroutinesApi::class)
//    fun addUser(user: User) {
//        saveUserSession(user.name, user.password)
//        GlobalScope.async {
//            userDao.insertUser(
//                UserEntity(
//                    username = user.name,
//                    password = user.password,
//                    profilePic = user.profilePic?: ByteArray(0)
//                )
//            )
//        }
//        users.add(user)
//    }
//    fun clearUserSession() {
//        settings.remove(KEY_USERNAME)
//        settings.remove(KEY_PASSWORD)
//        settings.putBoolean(KEY_LOGGED_IN, false)
//        users.clear()
//    }
//
//    fun isLoggedIn(): Boolean {
//        return settings.getBoolean(KEY_LOGGED_IN, defaultValue = false)
//    }
}
