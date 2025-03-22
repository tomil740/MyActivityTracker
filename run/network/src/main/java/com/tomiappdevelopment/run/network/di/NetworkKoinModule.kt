package com.tomiappdevelopment.run.network.di


import com.tomiappdevelopment.core.domain.run.RemoteRunDataSource
import com.tomiappdevelopment.run.network.KtorRemoteRunDataSource
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val networkModule = module {
    singleOf(::KtorRemoteRunDataSource).bind<RemoteRunDataSource>()
}