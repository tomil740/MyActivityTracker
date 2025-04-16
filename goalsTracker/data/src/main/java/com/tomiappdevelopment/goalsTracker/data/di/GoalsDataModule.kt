package com.tomiappdevelopment.goalsTracker.data.di

import androidx.room.Room
import com.tomiappdevelopment.goalsTracker.data.WeeklyGoalsRepositoryImpl
import com.tomiappdevelopment.goalsTracker.data.local.GoalsDatabase
import com.tomiappdevelopment.goalstracker.domain.WeeklyGoalsRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val GoalsDataModule = module {

    single {
        Room.databaseBuilder(
            context = androidContext(),
            klass = GoalsDatabase::class.java,
            name = "goals_db"
        ).fallbackToDestructiveMigration()
            .build()
    }

    // Provide DAO
    single { get<GoalsDatabase>().WeeklyGoalsDao }


    single<WeeklyGoalsRepository> { WeeklyGoalsRepositoryImpl(get()) }

}