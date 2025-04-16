package com.tomiappdevelopment.goalstracker.presentation.homeScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tomiappdevelopment.core.domain.run.ActivitiesRepository
import com.tomiappdevelopment.goalstracker.domain.WeeklyGoalsRepository
import com.tomiappdevelopment.goalstracker.domain.useCase.GetWeekSummaryUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeScreenViewModel(
    private val activitiesRepository: ActivitiesRepository,
    private val getWeekSummaryUseCase: GetWeekSummaryUseCase,
    private val weeklyGoalsRepository: WeeklyGoalsRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(HomeScreenState())
    var uiState = _uiState.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), _uiState.value)


    init {
        viewModelScope.launch {
            combine(
                weeklyGoalsRepository.observeGoals(),
                activitiesRepository.getWeekActivities()
            ) { goals, activities ->
                goals to activities
            }.collect { (goals, activities) ->

                val summaryResult = getWeekSummaryUseCase.invoke(
                    weekActivities = activities,
                    targets = goals
                )

                _uiState.update {
                    it.copy(
                        weeklyTargets = goals,
                        weekActivities = activities,
                        weekState = summaryResult.summary,
                        statusSentence = summaryResult.feedbackText,
                        performanceScore = summaryResult.performanceScore
                    )
                }
            }
        }

    viewModelScope.launch {
            activitiesRepository.syncPendingRuns()
            activitiesRepository.fetchRuns()
        }
    }



    fun onAction(action: HomeScreenEvents) {
        when (action) {
            HomeScreenEvents.OnLogoutClick -> TODO()
            else -> Unit
        }
    }
}