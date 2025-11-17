package org.example.project.storage

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    val username: String,
    val password: String,
    val profilePic: ByteArray = ByteArray(0),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UserEntity

        if (uid != other.uid) return false
        if (username != other.username) return false
        if (password != other.password) return false
        if (!profilePic.contentEquals(other.profilePic)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = uid.hashCode()
        result = 31 * result + username.hashCode()
        result = 31 * result + password.hashCode()
        result = 31 * result + (profilePic.contentHashCode())
        return result
    }
}

@Entity
data class MessageEntity(
    @PrimaryKey(autoGenerate = true) val messageId: Long = 0,
    val uidFrom: Int,
    val uidTo: Int?,
    val message: String,
    val timeInMillis: Long = System.currentTimeMillis()
)
