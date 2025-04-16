package com.tomiappdevelopment.goalstracker.presentation.SetGoalsScreen

data class SetGoalsState(
    val distanceGoal: Int = 5,
    val workoutGoal: Int = 3,
    val isSaving: Boolean = false,
)
