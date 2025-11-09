import android.content.Context
import androidx.datastore.core.DataStore
import org.example.project.Junk.DATA_STORE_FILE_NAME
import org.example.project.Junk.createDataStore
import androidx.datastore.preferences.core.Preferences

fun createDataStore(context: Context): DataStore<Preferences> {
    return createDataStore {
        context.filesDir.resolve(DATA_STORE_FILE_NAME).absolutePath
    }
}