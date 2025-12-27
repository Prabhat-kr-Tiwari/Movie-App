package com.prabhat.movieapp.data.local.userPrefrence

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "userPreference")
data class UserPreferenceEntity(
    @PrimaryKey
    val id:Int=1,
    val plans:String="",
    val paymentMode:String="",
    val avatar:String="",
    val userName:String="",
    val password:String="",
    val pin: String=""
)
