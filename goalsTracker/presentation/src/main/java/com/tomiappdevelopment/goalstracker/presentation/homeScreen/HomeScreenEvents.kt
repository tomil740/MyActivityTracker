package com.tomiappdevelopment.goalstracker.presentation.homeScreen

sealed interface HomeScreenEvents {
    data object OnStartActivity: HomeScreenEvents
    data object OnLogoutClick: HomeScreenEvents
    data object OnSetGoals: HomeScreenEvents
    data object OnAllActivities: HomeScreenEvents
}