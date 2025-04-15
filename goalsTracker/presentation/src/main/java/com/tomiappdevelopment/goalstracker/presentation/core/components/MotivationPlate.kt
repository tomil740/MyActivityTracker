package com.tomiappdevelopment.goalstracker.presentation.core.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun MotivationPlate(
    message: String,
    grade: Int,
    modifier: Modifier = Modifier
) {
    val backgroundColor: Color
    val textColor: Color
    val icon: ImageVector
    val style: TextStyle

    when (grade) {
        in 0..3 -> {
            backgroundColor = MaterialTheme.colorScheme.errorContainer
            textColor = MaterialTheme.colorScheme.onErrorContainer
            icon = Icons.Default.Warning
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
        }
        in 4..7 -> {
            backgroundColor = MaterialTheme.colorScheme.secondaryContainer
            textColor = MaterialTheme.colorScheme.onSecondaryContainer
            icon = Icons.Default.Info
            style = MaterialTheme.typography.bodyLarge
        }
        else -> {
            backgroundColor = MaterialTheme.colorScheme.primaryContainer
            textColor = MaterialTheme.colorScheme.onPrimaryContainer
            icon = Icons.Default.ThumbUp
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
        }
    }

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        color = backgroundColor,
        shape = RoundedCornerShape(16.dp),
        tonalElevation = 1.dp
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = textColor,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = message,
                style = style,
                color = textColor,
                modifier = Modifier.weight(1f)
            )
        }
    }
}
@Preview(showBackground = true)
@Composable
fun MotivationPlatePreview() {
    Column {
        MotivationPlate("You’re not on track, focus more this week", grade = 2)
        MotivationPlate("You're doing okay, keep it up!", grade = 5)
        MotivationPlate("Crushing your goals! Keep it going 💪", grade = 9)
    }
}

