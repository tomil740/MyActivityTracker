package com.tomiappdevelopment.goalsTracker.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tomiappdevelopment.core.database.dao.RunDao

@Database(
    entities = [
        WeeklyGoalsEntity::class
    ],
    version = 1
)
abstract class GoalsDatabase: RoomDatabase() {

    abstract val WeeklyGoalsDao: WeeklyGoalsDao
}