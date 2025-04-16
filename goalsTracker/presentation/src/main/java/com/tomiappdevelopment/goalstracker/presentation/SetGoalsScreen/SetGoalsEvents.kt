package com.tomiappdevelopment.goalstracker.presentation.SetGoalsScreen

import com.tomiappdevelopment.goalstracker.presentation.homeScreen.HomeScreenEvents

sealed class SetGoalsEvents{
    data class OnDistanceGoalChange(val value: Int) : SetGoalsEvents()
    data class OnWorkoutGoalChange(val value: Int) : SetGoalsEvents()
    object OnSaveClick : SetGoalsEvents()
    object OnBackClick : SetGoalsEvents()
    object OnStartActivityClick : SetGoalsEvents()
    data object OnLogoutClick: SetGoalsEvents()

}
