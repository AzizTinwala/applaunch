package io.applaunch.applaunchmini.repository.databaseRoom.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "User")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val firstName:String,
    val lastName:String,
    val eMail:String


) {
    override fun toString(): String {
        return "User(id=$id, firstName='$firstName', lastName='$lastName', eMail='$eMail')"
    }
}