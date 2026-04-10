package com.prabhat.movieapp.mappers.usePreference

import com.prabhat.movieapp.data.local.userPrefrence.UserPreferenceEntity
import com.prabhat.movieapp.domain.model.userPreference.UserPreference
import com.prabhat.movieapp.presentation.screen.profileScreen.chooseAvatarScreen.UserPreferenceUiModel

fun UserPreferenceEntity.toDomain(): UserPreference{

    return UserPreference(
        plans = plans?:"",
        paymentMode = paymentMode,
        avatar = avatar,
        userName = userName,
        password = password,
        pin = pin
    )

}
fun UserPreference.toPresentation(): UserPreferenceUiModel{

    return UserPreferenceUiModel(
        plans = plans?:"",
        paymentMode = paymentMode,
        avatar = avatar,
        userName = userName,
        password = password,
        pin = pin
    )

}