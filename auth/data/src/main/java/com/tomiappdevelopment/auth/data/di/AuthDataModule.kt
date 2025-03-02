package com.tomiappdevelopment.auth.data.di

import com.tomiappdevelopment.auth.domain.UserDataValidator
import com.tomiappdevelopment.auth.data.EmailPatternValidator
import com.tomiappdevelopment.auth.domain.AuthRepository
import com.tomiappdevelopment.auth.data.AuthRepositoryImpl
import com.tomiappdevelopment.auth.domain.PatternValidator
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val authDataModule = module {
    single<PatternValidator> {
        EmailPatternValidator
    }
    singleOf(::UserDataValidator)
    singleOf(::AuthRepositoryImpl).bind<AuthRepository>()

}