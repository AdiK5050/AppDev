package org.example.project.storage

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("INSERT INTO UserEntity (username, password) VALUES (:username, :password)")
    suspend fun insertUser(username: String, password: String)

    @Query("SELECT * FROM UserEntity")
    fun getAllAsFlow(): Flow<List<UserEntity>>
    @Query("SELECT * FROM UserEntity")
    suspend fun getAll(): List<UserEntity>

    @Query("SELECT profilePic FROM UserEntity where uid = :uid")
    suspend fun getPicByUID(uid: Int): ByteArray

    @Query("UPDATE UserEntity SET profilePic = :profilePic WHERE uid = :uid")
    suspend fun setPicByUID(uid:Int, profilePic: ByteArray)

    @Query("SELECT * FROM UserEntity WHERE username = :username")
    suspend fun getByName(username: String): UserEntity?
}
@Dao
interface MessageDao {

    @Query("SELECT * FROM MessageEntity")
    fun getAllAsFlow(): Flow<List<MessageEntity>>

    @Insert
    suspend fun insert(message: MessageEntity)

    @Query("SELECT * FROM MessageEntity WHERE messageId = :messageId ORDER BY timeInMillis DESC")
    suspend fun getByMessageId(messageId: Int): List<MessageEntity>
}