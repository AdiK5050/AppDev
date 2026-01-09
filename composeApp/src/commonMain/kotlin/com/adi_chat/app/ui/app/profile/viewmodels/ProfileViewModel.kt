package com.adi_chat.app.ui.app.profile.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adi_chat.app.storage.AppDatabase
import com.adi_chat.app.storage.UserEntity
import com.adi_chat.app.toByteArray
import com.adi_chat.app.shared.viewmodels.SharedViewModel
import com.wannaverse.imageselector.ImageData
import com.wannaverse.imageselector.selectImage
import com.wannaverse.imageselector.toImageBitmap
import kotlinx.coroutines.launch

class ProfileViewModel(val appDatabase: AppDatabase, sharedViewModel: SharedViewModel) : ViewModel() {

    val userDao = appDatabase.getUserDao()
    val userID = mutableIntStateOf(sharedViewModel.currentUserID.intValue)
    val user: MutableState<UserEntity> = mutableStateOf(UserEntity(userID.intValue, "", ""))
    var image = mutableStateOf<ImageData?>(null)
    var profilePic: MutableState<ByteArray?> = mutableStateOf(ByteArray(0))
    var name: MutableState<String> = mutableStateOf("")
    var friendAdded = mutableStateOf(false)

    fun initAll() {
        println("UserID: ${userID.intValue}")
        println("UserName: ${name.value}")
        println("profilePic value: ${profilePic.value}")
        viewModelScope.launch {
            user.value = userDao.getUserByUserID(userID.intValue)
            name.value = user.value.username
            profilePic.value = user.value.profilePic
            println("UserName: ${name.value}")
            println("profilePic value: ${profilePic.value}")
        }
    }
    fun chooseImage() = viewModelScope.launch {
        image.value = selectImage()
        if(image.value != null) {
            profilePic.value = image.value?.bytes?.toImageBitmap()?.toByteArray()
            userDao.setPicByUID(userID.intValue, image.value!!.bytes!!.toImageBitmap().toByteArray())
        }
    }
}