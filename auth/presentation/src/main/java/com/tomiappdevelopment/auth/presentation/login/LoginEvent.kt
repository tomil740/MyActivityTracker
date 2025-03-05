package com.tomiappdevelopment.auth.presentation.login

import com.tomiappdevelopment.presentation.ui.UiText

sealed interface LoginEvent {
    data class Error(val error: UiText): LoginEvent
    data object LoginSuccess: LoginEvent
}