package com.tomiappdevelopment.core.data.di

import com.tomiappdevelopment.core.data.networking.HttpClientFactory
import com.tomiappdevelopment.core.domain.SessionStorage
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import com.tomiappdevelopment.core.data.auth.EncryptedSessionStorage
import com.tomiappdevelopment.core.domain.run.RunRepository
import org.koin.dsl.bind
import com.tomiappdevelopment.core.data.run.OfflineFirstRunRepository

val coreDataModule = module {
    single {
        HttpClientFactory(get()).build()
    }
    singleOf(::EncryptedSessionStorage).bind<SessionStorage>()
    singleOf(::OfflineFirstRunRepository).bind<RunRepository>()

}