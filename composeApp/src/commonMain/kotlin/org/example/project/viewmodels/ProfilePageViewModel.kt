package org.example.project.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wannaverse.imageselector.ImageData
import com.wannaverse.imageselector.selectImage
import com.wannaverse.imageselector.toImageBitmap
import kotlinx.coroutines.launch

class ProfilePageViewModel(val database: Database) : ViewModel() {
    var image = mutableStateOf<ImageData?>(null)

    var name = mutableStateOf("Rias Gremory")
    var friendAdded = mutableStateOf(false)
    fun setProfilePic(){
        if(image.value != null)
            database.setProfilePic( image.value?.bytes?.toImageBitmap() as ImageBitmap)
    }
    fun chooseImage() = viewModelScope.launch {
        image.value = selectImage()
        setProfilePic()
    }
}