package com.tomiappdevelopment.auth.presentation.di

import com.tomiappdevelopment.auth.presentation.register.RegisterViewModel
import com.tomiappdevelopment.auth.presentation.login.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val authViewModelModule = module {
    viewModelOf(::RegisterViewModel)
    viewModelOf(::LoginViewModel)
}