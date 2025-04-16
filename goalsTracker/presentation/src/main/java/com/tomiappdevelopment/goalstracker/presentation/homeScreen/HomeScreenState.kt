package com.tomiappdevelopment.goalstracker.presentation.homeScreen

import com.tomiappdevelopment.core.domain.modules.ActivityGoalsData
import com.tomiappdevelopment.goalstracker.domain.modules.WeekDataSum
import java.time.ZonedDateTime
import kotlin.time.Duration

data class HomeScreenState(
    val weeklyTargets:WeekDataSum = WeekDataSum(2,622.2f),
    val weekState:WeekDataSum = WeekDataSum(3,28.7f),
    val statusSentence:String = "",
    val performanceScore:Int = 5,
    val weekActivities:List<ActivityGoalsData> = listOf(
    )

)
