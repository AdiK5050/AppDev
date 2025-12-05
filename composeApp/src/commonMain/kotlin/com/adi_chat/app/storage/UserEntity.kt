package com.adi_chat.app.storage

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val userID: Int = 0,
    val username: String,
    val password: String,
    val profilePic: ByteArray? = ByteArray(0),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UserEntity

        if (userID != other.userID) return false
        if (username != other.username) return false
        if (password != other.password) return false
        if (!profilePic.contentEquals(other.profilePic)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = userID.hashCode()
        result = 31 * result + username.hashCode()
        result = 31 * result + password.hashCode()
        result = 31 * result + (profilePic.contentHashCode())
        return result
    }
}

@Entity(
    foreignKeys = arrayOf(
        ForeignKey(
        entity = UserEntity::class,
        parentColumns = ["userID"],
        childColumns = ["senderID"],
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE,
        ),
        ForeignKey(
            entity = ChannelEntity::class,
            parentColumns = ["channelID"],
            childColumns = ["channelID"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE,
        )
    )
)
data class MessageEntity(
    @PrimaryKey(autoGenerate = true) val messageId: Long = 0,
    val senderID: Int,
    val channelID: Int,
    val message: String,
    val timeInMillis: Long = System.currentTimeMillis()
)

@Entity(
    foreignKeys = arrayOf(
        ForeignKey(
        entity = UserEntity::class,
        parentColumns = ["userID"],
        childColumns = ["userCreatedID"],
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE,
        )
    )
)
data class ChannelEntity(
    @PrimaryKey(autoGenerate = true) val channelID: Int = 0,
    val channelName: String,
    val userCreatedID: Int,
    val numberOfMembers: Int,
)

@Entity(
    foreignKeys = arrayOf(
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["userID"],
            childColumns = ["memberID"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE,
        ),
        ForeignKey(
            entity = ChannelEntity::class,
            parentColumns = ["channelID"],
            childColumns = ["channelID"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE,
        )
    )
)
data class ChannelMembers(
    @PrimaryKey(autoGenerate = true) val channelMembersID: Int = 0,
    val channelID: Int,
    val memberID: Int,
)