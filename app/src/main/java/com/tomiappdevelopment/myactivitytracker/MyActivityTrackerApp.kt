package com.tomiappdevelopment.myactivitytracker

import android.app.Application
import com.tomiappdevelopment.auth.data.di.authDataModule
import com.tomiappdevelopment.auth.presentation.di.authViewModelModule
import com.tomiappdevelopment.core.data.di.coreDataModule
import com.tomiappdevelopment.core.database.di.databaseModule
import com.tomiappdevelopment.goalsTracker.data.di.GoalsDataModule
import com.tomiappdevelopment.goalstracker.presentation.di.goalsTrackerPresentationModule
import com.tomiappdevelopment.myactivitytracker.di.appModule
import com.tomiappdevelopment.run.data.di.runDataModule
import com.tomiappdevelopment.run.location.di.locationModule
import com.tomiappdevelopment.run.network.di.networkModule
import com.tomiappdevelopment.run.presentation.di.runPresentationModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.workmanager.koin.workManagerFactory
import org.koin.core.context.startKoin
import timber.log.Timber

class MyActivityTrackerApp: Application() {

    val applicationScope = CoroutineScope(SupervisorJob())

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidLogger()
            androidContext(this@MyActivityTrackerApp)
            workManagerFactory()
            modules(
                authDataModule,
                authViewModelModule,
                appModule,
                coreDataModule,
                runPresentationModule,
                locationModule,
                databaseModule,
                networkModule,
                runDataModule,
                goalsTrackerPresentationModule,
                GoalsDataModule
            )
        }
    }
}