package com.tomiappdevelopment.auth.presentation.register

import com.tomiappdevelopment.presentation.ui.UiText

sealed interface RegisterEvent {
    data object RegistrationSuccess: RegisterEvent
    data class Error(val error: UiText): RegisterEvent
}