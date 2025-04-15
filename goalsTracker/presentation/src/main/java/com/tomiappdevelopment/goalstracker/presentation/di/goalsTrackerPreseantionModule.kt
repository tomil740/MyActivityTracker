package com.tomiappdevelopment.goalstracker.presentation.di

import com.tomiappdevelopment.goalstracker.presentation.homeScreen.HomeScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val goalsTrackerPresentationModule = module {
    viewModelOf(::HomeScreenViewModel)
}