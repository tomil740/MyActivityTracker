package com.tomiappdevelopment.goalstracker.presentation.SetGoalsScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tomiappdevelopment.goalstracker.domain.WeeklyGoalsRepository
import com.tomiappdevelopment.goalstracker.domain.modules.WeekDataSum
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SetGoalsViewModel(
    private val weeklyGoalsRepository:WeeklyGoalsRepository
    //private val sessionStorage: SessionStorage
): ViewModel() {

    private val _uiState = MutableStateFlow(SetGoalsState())
    var uiState = _uiState.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), _uiState.value)

    // SharedFlow for error messages
    private val _uiMessage = Channel<String>()
    val uiMessage: Channel<String> get() = _uiMessage


    init {
        viewModelScope.launch {
            weeklyGoalsRepository.observeGoals().collect{ newVal->
                if(newVal!=null) {
                    _uiState.update {
                        it.copy(
                            distanceGoal = newVal.distance.toInt() ,
                            workoutGoal = newVal.quantity
                        )
                    }
                }

            }
        }
    }

    fun onAction(event:SetGoalsEvents){
        when(event){
            is SetGoalsEvents.OnDistanceGoalChange ->{
                _uiState.update { it.copy(distanceGoal = event.value) }
            }

            SetGoalsEvents.OnSaveClick -> {
                viewModelScope.launch {
                    _uiState.update { it.copy(isSaving = true) }
                    withContext(Dispatchers.IO) {
                        weeklyGoalsRepository.saveGoals(
                            WeekDataSum(
                                distance = _uiState.value.distanceGoal.toFloat(),
                                quantity = _uiState.value.workoutGoal
                            )
                        )
                    }
                    _uiState.update { it.copy(isSaving = false) }
                    uiMessage.send("Goals was update successfully")
                }
            }

            is SetGoalsEvents.OnWorkoutGoalChange -> {
                _uiState.update { it.copy(workoutGoal = event.value) }
            }
            else -> Unit
        }
    }


}