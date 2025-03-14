package com.tomiappdevelopment.auth.presentation.login

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text2.input.textAsFlow
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tomiappdevelopment.auth.domain.AuthRepository
import com.tomiappdevelopment.auth.domain.UserDataValidator
import com.tomiappdevelopment.auth.presentation.R
import com.tomiappdevelopment.core.domain.util.DataError
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import  com.tomiappdevelopment.core.domain.util.Result
import com.tomiappdevelopment.presentation.ui.UiText
import com.tomiappdevelopment.presentation.ui.asUiText

@OptIn(ExperimentalFoundationApi::class)
class LoginViewModel(
    private val authRepository: AuthRepository,
    private val userDataValidator: UserDataValidator
): ViewModel() {

    var state by mutableStateOf(LoginState())
        private set

    private val eventChannel = Channel<LoginEvent>()
    val events = eventChannel.receiveAsFlow()

    init {
        combine(state.email.textAsFlow(), state.password.textAsFlow()) { email, password ->
            state = state.copy(
                canLogin = userDataValidator.isValidEmail(
                    email = email.toString().trim()
                ) && password.isNotEmpty()

            )
        }.launchIn(viewModelScope)
    }

    fun onAction(action: LoginAction) {
        when(action){
            LoginAction.OnLoginClick ->
                login()

            LoginAction.OnRegisterClick -> TODO()

            LoginAction.OnTogglePasswordVisibility -> {
                state = state.copy(
                    isPasswordVisible = !state.isPasswordVisible
                )
            }
        }
    }

    private fun login() {
        viewModelScope.launch {
            state = state.copy(isLoggingIn = true)
            val result = authRepository.login(
                email = state.email.text.toString().trim(),
                password = state.password.text.toString()
            )
            state = state.copy(isLoggingIn = false)

            when(result) {
                is Result.Error -> {
                    Log.i("the result",result.toString())
                    if(result.error == DataError.Network.UNAUTHORIZED) {
                        eventChannel.send(LoginEvent.Error(
                            UiText.StringResource(R.string.error_email_password_incorrect)
                        ))
                    } else {
                        eventChannel.send(LoginEvent.Error(result.error.asUiText()))
                    }
                }
                is Result.Success -> {
                    eventChannel.send(LoginEvent.LoginSuccess)
                }
            }
        }
    }
}