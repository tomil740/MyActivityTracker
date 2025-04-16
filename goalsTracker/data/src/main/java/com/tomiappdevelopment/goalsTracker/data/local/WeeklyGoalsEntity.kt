package com.tomiappdevelopment.goalsTracker.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weekly_goals")
data class WeeklyGoalsEntity(
    @PrimaryKey val id: Int = 0, // Always single record
    val runningDistanceKm: Float,
    val workoutCount: Int
)