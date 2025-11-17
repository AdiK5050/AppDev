package org.example.project.viewmodels

import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.ViewModel

class SharedViewModel: ViewModel() {
    val uidForProfile = mutableIntStateOf(0)
}