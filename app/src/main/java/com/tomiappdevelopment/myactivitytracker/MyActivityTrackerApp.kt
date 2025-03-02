package com.tomiappdevelopment.myactivitytracker

import android.app.Application
import com.tomiappdevelopment.auth.data.di.authDataModule
import com.tomiappdevelopment.auth.presentation.di.authViewModelModule
import com.tomiappdevelopment.core.data.di.coreDataModule
import com.tomiappdevelopment.myactivitytracker.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class MyActivityTrackerApp: Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidLogger()
            androidContext(this@MyActivityTrackerApp)
            modules(
                authDataModule,
                authViewModelModule,
                appModule,
                coreDataModule
            )
        }
    }
}