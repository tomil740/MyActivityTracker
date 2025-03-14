package com.tomiappdevelopment.run.presentation.active_run

import com.tomiappdevelopment.presentation.ui.UiText


sealed interface ActiveRunEvent {
    data class Error(val error: UiText): ActiveRunEvent
    data object RunSaved: ActiveRunEvent
}