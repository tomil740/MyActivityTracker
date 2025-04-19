package com.tomiappdevelopment.goalstracker.domain.useCase


import com.tomiappdevelopment.core.domain.modules.ActivityGoalsData
import com.tomiappdevelopment.goalstracker.domain.modules.WeekDataSum
import com.tomiappdevelopment.goalstracker.domain.modules.WeekProgressFeedback
import java.time.LocalDate

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


class GetWeekSummaryUseCase {

    operator fun invoke(
        weekActivities: List<ActivityGoalsData>,
        targets: WeekDataSum
    ): WeekProgressFeedback {
        // Handle edge case early
        if (weekActivities.isEmpty() || (targets.quantity == 0 && targets.distance.toFloat() == 0f)) {
            return WeekProgressFeedback(
                summary = WeekDataSum(0, 0f),
                feedbackText = "Set your weekly goals to get started!",
                performanceScore = 0
            )
        }
        // Step 1: Aggregate activity data
        val summary = weekActivities.fold(WeekDataSum(0, 0f)) { acc, activity ->
            WeekDataSum(
                quantity = acc.quantity + 1,
                distance = acc.distance + activity.distance
            )
        }

        // Step 2: Calculate portion of the week passed (0.0 - 1.0)
        val today = LocalDate.now()
        val dayOfWeek = today.dayOfWeek.value.coerceAtMost(7) // Monday = 1, Sunday = 7
        val portionOfWeek = dayOfWeek / 7.0

        // Step 3: Compute ideal state by now
        val idealWorkouts = targets.quantity * portionOfWeek
        val idealDistance = targets.distance * portionOfWeek

        // Step 4: Check how close we are (±5%)
        fun evaluate(actual: Double, ideal: Double): Int {
            if (ideal == 0.0) return 5 // Avoid divide by 0
            val diff = (actual - ideal) / ideal
            return when {
                diff < -0.05 -> 2 // Behind
                diff > 0.05 -> 9 // Ahead
                else -> 6 // On track
            }
        }

        val workoutScore = evaluate(summary.quantity.toDouble(), idealWorkouts)
        val distanceScore = evaluate(summary.distance.toDouble(), idealDistance)

        // Step 5: Average and clamp
        val avgScore = ((workoutScore + distanceScore) / 2).toInt().coerceIn(0, 10)

        val feedback = when (avgScore) {
            in 0..3 -> "You're falling behind your goals, pick up the pace!"
            in 4..7 -> "You're on track, keep it up!"
            in 8..10 -> "Amazing progress! You're crushing your goals!"
            else -> "Keep moving!"
        }

        return WeekProgressFeedback(
            summary = summary,
            feedbackText = feedback,
            performanceScore = avgScore
        )
    }
}


