package com.prabhat.movieapp.data.repository.userPreference

import com.prabhat.movieapp.data.local.userPrefrence.UserPreferenceDao
import com.prabhat.movieapp.data.local.userPrefrence.UserPreferenceEntity
import com.prabhat.movieapp.domain.repository.userPreference.UserPreferenceRepository
import javax.inject.Inject

class UserPreferenceRepositoryImpl @Inject constructor(private val userPreferenceDao: UserPreferenceDao) :
    UserPreferenceRepository{
    override suspend fun savePreference(pre: UserPreferenceEntity) {
        userPreferenceDao.upsertPreference(pre)
    }

    override suspend fun updateField(field: String, value: String) {
        val current = userPreferenceDao.getUserPreference() ?: UserPreferenceEntity()


        val updated = when (field) {
            "plans" -> current.copy(plans = value)
            "paymentMode" -> current.copy(paymentMode = value)
            "avatar" -> current.copy(avatar = value)
            "userName" -> current.copy(userName = value)
            "password" -> current.copy(password = value)
            "pin" -> current.copy(pin = value)
            else -> current
        }

        userPreferenceDao.upsertPreference(updated)
    }

    override suspend fun getPreference(): UserPreferenceEntity? {

       return userPreferenceDao.getUserPreference()
    }

    override suspend fun clearPreference() {
        userPreferenceDao.clearAll()
    }
}