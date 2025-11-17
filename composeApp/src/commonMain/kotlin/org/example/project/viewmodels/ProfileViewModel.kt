package org.example.project.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.sqlite.throwSQLiteException
import com.wannaverse.imageselector.ImageData
import com.wannaverse.imageselector.selectImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okio.ByteString.Companion.toByteString
import org.example.project.storage.Database
import org.example.project.storage.UserEntity
import kotlin.math.absoluteValue
import kotlin.math.log

class ProfileViewModel(val database: Database) : ViewModel() {

    var uid = mutableStateOf(0)
    var image = mutableStateOf<ImageData?>(null)
    var profilePic = mutableStateOf<ByteArray>(ByteArray(1))

    var name = mutableStateOf("")
    var friendAdded = mutableStateOf(false)

    fun init() {
        this.uid.value = database.uidToProfilePage.value
        viewModelScope.launch(Dispatchers.Main) {
            database.userEntity.value.forEach { userEntity ->
                if (userEntity.uid == uid.value) {
                } else  {
                    println("Couldn't fetch user data in ProfileViewModel")
                }
            }
        }
    }
    fun setProfilePic(profilePic: ByteArray) = viewModelScope.launch {
        database.userDao.setPicByUID(uid.value, profilePic)
    }
    fun chooseImage() = viewModelScope.launch {
        image.value = selectImage()
        if(image.value != null)
            setProfilePic(profilePic.value)
    }
}