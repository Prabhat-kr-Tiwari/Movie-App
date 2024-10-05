package com.prabhat.movieapp.presentation.screen.introScreen

import com.prabhat.movieapp.data.appSettings.SessionId

data class

IntroScreenState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val sessionId: SessionId? = null
)
