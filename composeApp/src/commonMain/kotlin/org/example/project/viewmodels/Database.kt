package org.example.project.viewmodels

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.riasgremory
import org.jetbrains.compose.resources.imageResource

data class User(val name: String, val password: String)

class Database {
    val users = mutableListOf<User>(User("admin","riasissexy"))
    private var profilePic: ImageBitmap = ImageBitmap(1,1)

    fun setProfilePic(pic: ImageBitmap) {
        profilePic = pic
    }
    fun addUser(user: User) {
        users.add(user)
    }
    fun getProfilePic(): ImageBitmap {
        return profilePic
    }
}