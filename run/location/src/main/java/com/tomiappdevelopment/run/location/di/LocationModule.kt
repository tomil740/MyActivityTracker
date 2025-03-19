package com.tomiappdevelopment.run.location.di


import com.tomiappdevelopment.run.domain.LocationObserver
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import com.tomiappdevelopment.run.location.AndroidLocationObserver

val locationModule = module {
    singleOf(::AndroidLocationObserver).bind<LocationObserver>()
}