package com.tomiappdevelopment.goalstracker.presentation.di

import com.tomiappdevelopment.goalstracker.presentation.homeScreen.HomeScreenViewModel
import com.tomiappdevelopment.goalstracker.presentation.SetGoalsScreen.SetGoalsViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import com.tomiappdevelopment.goalstracker.domain.useCase.GetWeekSummaryUseCase


val goalsTrackerPresentationModule = module {
    viewModelOf(::SetGoalsViewModel)
    viewModelOf(::HomeScreenViewModel)
    singleOf(::GetWeekSummaryUseCase)
}