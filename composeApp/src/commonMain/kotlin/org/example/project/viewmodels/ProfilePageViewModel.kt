package org.example.project.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wannaverse.imageselector.ImageData
import com.wannaverse.imageselector.selectImage
import com.wannaverse.imageselector.toByteArray
import com.wannaverse.imageselector.toImageBitmap
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ProfilePageViewModel(val database: Database) : ViewModel() {
    var imageBitmap = mutableStateOf<ImageBitmap?>(database.profilePicInDB.value)
    var image = mutableStateOf<ImageData?>(null)

    var name = mutableStateOf("Rias Gremory")
    var friendAdded = mutableStateOf(false)

    fun init() = viewModelScope.launch {
        image.value = imageBitmapToByteArrary()
    }
    fun setProfilePic(){
        database.setProfilePic( image.value?.bytes?.toImageBitmap() as ImageBitmap)
    }
    fun imageBitmapToByteArrary(): ImageData? {
        return ImageData(imageBitmap.value?.toByteArray())
    }
    fun chooseImage() = viewModelScope.launch {
        image.value = selectImage()
        delay(1000)
        setProfilePic()
    }
}