package org.example.project.junk

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import okio.Path.Companion.toPath

//This is for KMP but android specific implementation is different and it's in the CreateDataStore.common.kt of androidMain

fun createDataStore(producePath: () -> String): DataStore<Preferences> {
    return PreferenceDataStoreFactory.createWithPath(
        produceFile = {producePath().toPath()}
    )
}
internal const val DATA_STORE_FILE_NAME = "prefs.preference_pb"