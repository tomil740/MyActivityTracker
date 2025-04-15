package com.tomiappdevelopment.goalstracker.domain.useCase

import com.tomiappdevelopment.goalstracker.domain.modules.ActivityGoalsData
import com.tomiappdevelopment.goalstracker.domain.modules.WeekDataSum
import com.tomiappdevelopment.goalstracker.domain.modules.WeekProgressFeedback

/**
 * GetWeekSummary Use Case
 *
 * Summarizes the week's activity data against user goals.
 *
 * Input:
 * - `weekActivities`: List of activities for the week.
 * - `targets`: WeekData (user’s target goals).
 *
 * Output:
 * - `WeekProgressFeedback`: Contains:
 *   - `summary`: WeekDataSum (aggregated data for the week).
 *   - `feedbackText`: Progress sentence (e.g., "You're halfway there!").
 *   - `performanceScore`: Performance grade (0-10).
 */


class GetWeekSummary {
    operator fun invoke(activities: List<ActivityGoalsData>,target:WeekDataSum):WeekProgressFeedback {
        return WeekProgressFeedback(
            summary = WeekDataSum(3,44.5),
            feedbackText = "Nice allmost there",
            performanceScore = 8
        )
    }
}