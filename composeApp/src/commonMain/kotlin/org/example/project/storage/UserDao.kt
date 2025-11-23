package org.example.project.storage

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(user: UserEntity)

    @Query("SELECT * FROM UserEntity")
    fun getAllAsFlow(): Flow<List<UserEntity>>

    @Query("SELECT userID FROM UserEntity WHERE username = :username")
    suspend fun getUIDByName(username: String): Int
    @Query("SELECT profilePic FROM UserEntity WHERE userID = :userID")
    suspend fun getPicByUID(userID: Int): ByteArray?

    @Query("UPDATE UserEntity SET profilePic = :profilePic WHERE userID = :userID")
    suspend fun setPicByUID(userID:Int, profilePic: ByteArray)

    @Query("SELECT * FROM UserEntity WHERE userID = :userID")
    suspend fun getByUID(userID: Int): UserEntity?
    @Query("SELECT * FROM UserEntity WHERE username = :username")
    suspend fun getByName(username: String): UserEntity?
}
@Dao
interface MessageDao {

    @Query("SELECT * FROM MessageEntity")
    fun getAllAsFlow(): Flow<List<MessageEntity>>

    @Insert
    suspend fun insertMessage(message: MessageEntity)

    @Query("SELECT message FROM MessageEntity WHERE channelID = :channelID ORDER BY timeInMillis DESC")
    suspend fun getLastMessageByChannelID(channelID: Int): String
}

@Dao
interface ChannelDao {
    @Insert
    suspend fun insertChannel(channel: ChannelEntity)

    @Query("SELECT * FROM ChannelEntity")
    fun getAllAsFlow(): Flow<List<ChannelEntity>>

}