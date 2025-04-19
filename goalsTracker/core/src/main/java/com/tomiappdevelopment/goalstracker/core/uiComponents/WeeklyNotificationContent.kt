package com.tomiappdevelopment.goalstracker.core.uiComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun WeeklyNotificationContent(
    distanceSum: Int,
    workoutsSum: Int,
    feedbackText: String,
    performanceScore: Int,
    onSetGoalsClick: () -> Unit,
    onRemindLaterClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        WeekOverview(
            workouts = workoutsSum.toFloat() to 1f, // Assuming goal is 1 workout for now
            distance = distanceSum.toFloat() to 1f, // Assuming goal is 1km for now
            onSetGoalsClick = onSetGoalsClick
        )

        Spacer(modifier = Modifier.height(12.dp))

        MotivationPlate(
            message = feedbackText,
            grade = performanceScore
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            TextButton(onClick = onSetGoalsClick) {
                Text("Open App")
            }

            TextButton(onClick = onRemindLaterClick) {
                Text("Remind Me Later")
            }
        }
    }
}
