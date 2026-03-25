package com.prabhat.movieapp.data.local.cardDetails

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cardDetails")
data class CardDetailsEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Int=1,
    val firstName:String="",
    val lastName:String="",
    val cardNumber:String="",
    val expirationDate:String="",
    val securityCode: String=""
)
