package com.tomiappdevelopment.goalstracker.domain.modules

data class WeekProgressFeedback(
    val summary: WeekDataSum,
    val feedbackText: String,
    val performanceScore: Int
)
