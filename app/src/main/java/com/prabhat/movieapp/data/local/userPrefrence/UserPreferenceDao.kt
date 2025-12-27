package com.prabhat.movieapp.data.local.userPrefrence

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface UserPreferenceDao {

    @Upsert
    suspend fun  upsertPreference(userPreference: UserPreferenceEntity)

    @Query("SELECT * FROM userPreference LIMIT 1")
    suspend fun getUserPreference(): UserPreferenceEntity?

    @Query("DELETE FROM userPreference")
    suspend fun clearAll();
}