package org.example.project.storage

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.ABORT
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.example.project.viewmodels.ChannelInfo

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
    suspend fun getUserByUserID(userID: Int): UserEntity

    @Query("SELECT * FROM UserEntity WHERE username = :username")
    suspend fun getByName(username: String): UserEntity?
}
@Dao
interface MessageDao {

    @Query("SELECT * FROM MessageEntity WHERE channelID = :channelID")
    fun getAllMessagesByChannelID(channelID: Int): Flow<List<MessageEntity>>

    @Insert
    suspend fun insertMessage(message: MessageEntity)

    @Query("SELECT message FROM MessageEntity WHERE channelID = :channelID ORDER BY timeInMillis DESC")
    suspend fun getLastMessageByChannelID(channelID: Int): String?
    @Query("SELECT timeInMillis FROM MessageEntity WHERE channelID = :channelID ORDER BY timeInMillis DESC")
    suspend fun getLastMessageTimeByChannelID(channelID: Int): Long?
}

@Dao
interface ChannelDao {
    @Insert(onConflict = ABORT)
    suspend fun insertChannel(channel: ChannelEntity)
    @Insert
    suspend fun insertChannelMember(channelMember: ChannelMembers)

    @Query("SELECT channelID FROM ChannelEntity WHERE channelName = :channelName AND userCreatedID = :userCreatedID")
    suspend fun getChannelIDByCandidateKey(channelName: String, userCreatedID: Int): Int

    @Query("SELECT CE.channelID, CE.channelName, T1.message AS lastMessage, T1.timeInMillis As lastMessageTime FROM ChannelEntity CE JOIN ChannelMembers CM ON CE.channelID = CM.channelID JOIN ( SELECT ME.channelID, ME.message, ME.timeInMillis, ROW_NUMBER() OVER (PARTITION BY ME.channelID ORDER BY ME.timeInMillis DESC) AS rn FROM MessageEntity ME ) AS T1 ON CE.channelID = T1.channelID WHERE CM.memberID = :memberID AND T1.rn = 1 ORDER BY T1.timeInMillis DESC;")

    fun getChannelInfoByMemberID(memberID: Int): Flow<List<ChannelInfo>>

    @Query("SELECT channelName FROM ChannelEntity WHERE channelID = :channelID")
    suspend fun getChannelNameByChannelID(channelID: Int): String

    @Query("SELECT memberID FROM ChannelMembers WHERE channelID = :channelID")
    suspend fun getMembersByChannelID(channelID: Int): List<Int>

    @Query("SELECT channelID FROM ChannelMembers WHERE memberID = :memberID")
    suspend fun getChannelsByMemberID(memberID: Int): List<Int>
}
