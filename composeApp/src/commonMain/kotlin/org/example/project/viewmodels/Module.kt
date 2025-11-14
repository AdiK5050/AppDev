package org.example.project.viewmodels

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.russhwolf.settings.Settings
import kotlinx.serialization.Serializable
import org.example.project.junk.DATA_STORE_FILE_NAME
import org.example.project.junk.createDataStore

@Serializable
object AppSetting {
    val dataStore: DataStore<Preferences> = createDataStore { DATA_STORE_FILE_NAME }
    val settings: Settings = Settings()

}