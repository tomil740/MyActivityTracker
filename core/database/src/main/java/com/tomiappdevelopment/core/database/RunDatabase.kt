package com.tomiappdevelopment.core.database


import androidx.room.Database
import androidx.room.RoomDatabase
import com.tomiappdevelopment.core.database.dao.RunDao
import com.tomiappdevelopment.core.database.dao.RunPendingSyncDao
import com.tomiappdevelopment.core.database.entity.DeletedRunSyncEntity
import com.tomiappdevelopment.core.database.entity.RunEntity
import com.tomiappdevelopment.core.database.entity.RunPendingSyncEntity

@Database(
    entities = [
        RunEntity::class,
        RunPendingSyncEntity::class,
        DeletedRunSyncEntity::class
    ],
    version = 2
)
abstract class RunDatabase: RoomDatabase() {

    abstract val runDao: RunDao
    abstract val runPendingSyncDao: RunPendingSyncDao
}