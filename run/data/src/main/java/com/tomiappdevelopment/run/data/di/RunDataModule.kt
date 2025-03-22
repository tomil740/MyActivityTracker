package com.tomiappdevelopment.run.data.di

import com.tomiappdevelopment.core.domain.run.SyncRunScheduler
import com.tomiappdevelopment.run.data.CreateRunWorker
import com.tomiappdevelopment.run.data.DeleteRunWorker
import com.tomiappdevelopment.run.data.FetchRunsWorker
import org.koin.androidx.workmanager.dsl.workerOf
import org.koin.core.module.dsl.singleOf
import com.tomiappdevelopment.run.data.SyncRunWorkerScheduler
import org.koin.dsl.bind
import org.koin.dsl.module

val runDataModule = module {
    workerOf(::CreateRunWorker)
    workerOf(::FetchRunsWorker)
    workerOf(::DeleteRunWorker)

    singleOf(::SyncRunWorkerScheduler).bind<SyncRunScheduler>()
}