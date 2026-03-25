package com.prabhat.movieapp.data.local.cardDetails

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface CardDetailsDao {

    @Upsert
    suspend fun upsertCardDetails(cardDetailsEntity: CardDetailsEntity)

    @Query("SELECT * FROM cardDetails LIMIT 1")
    suspend fun getCardDetails(): CardDetailsEntity?

    @Query("DELETE FROM cardDetails")
    suspend fun clearCardDetails()
}