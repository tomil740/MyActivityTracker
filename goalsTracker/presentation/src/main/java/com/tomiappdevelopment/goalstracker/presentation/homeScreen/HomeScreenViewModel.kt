package com.tomiappdevelopment.goalstracker.presentation.homeScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class HomeScreenViewModel(
//    private val runRepository: RunRepository,
  //  private val syncRunScheduler: SyncRunScheduler,
    //private val applicationScope: CoroutineScope,
    //private val sessionStorage: SessionStorage
): ViewModel() {

    var state by mutableStateOf(HomeScreenState())
        private set


    fun onAction(action: HomeScreenEvents) {
        when (action) {
            HomeScreenEvents.OnAllActivities -> TODO()
            HomeScreenEvents.OnLogoutClick -> TODO()
            HomeScreenEvents.OnSetGoals -> TODO()
            else -> Unit
        }
    }
}