package com.tomiappdevelopment.core.data.di

import com.tomiappdevelopment.core.data.networking.HttpClientFactory
import org.koin.dsl.module

val coreDataModule = module {
    single {
        HttpClientFactory().build()
    }
}