package org.example.project.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wannaverse.imageselector.ImageData
import com.wannaverse.imageselector.selectImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.example.project.storage.AppDatabase

class ProfileViewModel(val appDatabase: AppDatabase) : ViewModel() {

    val userDao = appDatabase.getUserDao()
    var uid = mutableStateOf(0)
    var image = mutableStateOf<ImageData?>(null)
    var profilePic = mutableStateOf<ByteArray>(ByteArray(0))
    var name = mutableStateOf("")
    var friendAdded = mutableStateOf(false)

    fun init() {
        viewModelScope.launch(Dispatchers.IO) {
                profilePic.value = userDao.getPicByUID(uid.value)!!
        }
    }
    fun chooseImage() = viewModelScope.launch {
        image.value = selectImage()
        if(image.value != null)
            userDao.setPicByUID(uid.value, profilePic.value)
    }
}