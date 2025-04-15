package com.tomiappdevelopment.goalstracker.presentation.core.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun WeekOverview(
    workouts: Pair<Float, Float>,
    distance: Pair<Float, Float>,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(24.dp),
        tonalElevation = 2.dp,
        color = MaterialTheme.colorScheme.surface
    ) {
        Row(
            modifier = Modifier
                .padding(24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Left text column
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Workouts
                Text(
                    text = "Workouts",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "${workouts.first.toInt()} / ${workouts.second.toInt()}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Distance
                Text(
                    text = "Distance",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "${"%.1f".format(distance.first)} km / ${"%.1f".format(distance.second)} km",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.secondary
                )
            }

            // Right side rings
            ProgressRings(
                progress1 = workouts,
                progress2 = distance,
                modifier = Modifier
                    .padding(start = 16.dp)
                    .size(120.dp)
            )
        }
    }
}
@Preview(showBackground = true)
@Composable
fun WeekOverviewPreview() {
    MaterialTheme {
        WeekOverview(
            workouts = 4f to 5f,
            distance = 12.5f to 20f
        )
    }
}
