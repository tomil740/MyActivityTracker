package com.tomiappdevelopment.goalstracker.presentation.homeScreen

import com.tomiappdevelopment.goalstracker.domain.modules.ActivityGoalsData
import com.tomiappdevelopment.goalstracker.domain.modules.WeekDataSum
import java.time.ZonedDateTime
import kotlin.time.Duration

data class HomeScreenState(
    val weeklyTargets:WeekDataSum = WeekDataSum(5,55.3),
    val weekState:WeekDataSum = WeekDataSum(3,28.7),
    val statusSentence:String = "",
    val weekActivities:List<ActivityGoalsData> = listOf(
        ActivityGoalsData("1",Duration.ZERO, dateTime = ZonedDateTime.now(),5.5,""),
        ActivityGoalsData("2",Duration.ZERO, dateTime = ZonedDateTime.now(),5.5,""),
        ActivityGoalsData("3",Duration.ZERO, dateTime = ZonedDateTime.now(),5.5,""),
        ActivityGoalsData("4",Duration.ZERO, dateTime = ZonedDateTime.now(),5.5,"")
    )

)
