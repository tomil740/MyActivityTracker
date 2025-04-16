package com.tomiappdevelopment.goalstracker.presentation.homeScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tomiappdevelopment.core.domain.run.ActivitiesRepository
import com.tomiappdevelopment.goalstracker.domain.useCase.GetWeekSummaryUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeScreenViewModel(
    private val activitiesRepository: ActivitiesRepository,
  //  private val syncRunScheduler: SyncRunScheduler,
    private val getWeekSummaryUseCase: GetWeekSummaryUseCase
    //private val applicationScope: CoroutineScope,
    //private val sessionStorage: SessionStorage
): ViewModel() {

    private val _uiState = MutableStateFlow(HomeScreenState())
    var uiState = _uiState.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), _uiState.value)


    init {
            //wil be with the new query by date...
        viewModelScope.launch {
            activitiesRepository.getWeekActivities().collect{ theLst->

               val a = getWeekSummaryUseCase.invoke(
                    weekActivities = theLst, targets = _uiState.value.weeklyTargets
                )
                _uiState.update { it.copy(
                    weekActivities = theLst,
                    weekState = a.summary,
                    statusSentence = a.feedbackText,
                    performanceScore = a.performanceScore
                ) }
            }
        }





        viewModelScope.launch {
            activitiesRepository.syncPendingRuns()
            activitiesRepository.fetchRuns()
        }
    }


    fun onAction(action: HomeScreenEvents) {
        when (action) {
            HomeScreenEvents.OnAllActivities -> TODO()
            HomeScreenEvents.OnLogoutClick -> TODO()
            HomeScreenEvents.OnSetGoals -> TODO()
            else -> Unit
        }
    }
}