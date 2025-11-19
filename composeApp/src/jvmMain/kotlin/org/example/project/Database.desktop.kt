package org.example.project

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import org.example.project.storage.AppDatabase
import java.io.File

fun getDatabaseBuilder(): AppDatabase {
    val dbFile = File("my_room.db")
    println(dbFile.absolutePath)
    return Room.databaseBuilder<AppDatabase>(
        name = dbFile.absolutePath,
    ).setQueryCoroutineContext(Dispatchers.IO)
        .setDriver(BundledSQLiteDriver())
        .build()
}