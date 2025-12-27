package com.prabhat.movieapp.domain.repository.userPreference

import com.prabhat.movieapp.data.local.userPrefrence.UserPreferenceEntity

interface UserPreferenceRepository {

    suspend fun savePreference(pre: UserPreferenceEntity)
    suspend fun updateField(field:String,value:String)
    suspend fun getPreference(): UserPreferenceEntity?
    suspend fun clearPreference()
}