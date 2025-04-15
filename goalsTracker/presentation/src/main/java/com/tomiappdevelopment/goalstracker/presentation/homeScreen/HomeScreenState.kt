package com.tomiappdevelopment.goalstracker.presentation.homeScreen

import com.tomiappdevelopment.goalstracker.domain.modules.ActivityGoalsData
import com.tomiappdevelopment.goalstracker.domain.modules.WeekDataSum

data class HomeScreenState(
    val weeklyTargets:WeekDataSum = WeekDataSum(0,0.0),
    val weekState:WeekDataSum = WeekDataSum(0,0.0),
    val statusSentence:String = "",
    val weekActivities:List<ActivityGoalsData> = emptyList()

)
