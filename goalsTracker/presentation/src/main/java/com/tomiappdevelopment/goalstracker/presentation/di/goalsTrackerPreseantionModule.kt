package com.tomiappdevelopment.goalstracker.presentation.di

import com.tomiappdevelopment.goalstracker.presentation.homeScreen.HomeScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import com.tomiappdevelopment.goalstracker.domain.useCase.GetWeekSummaryUseCase

val goalsTrackerPresentationModule = module {
    viewModelOf(::HomeScreenViewModel)
    singleOf(::GetWeekSummaryUseCase)
}