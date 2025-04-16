package com.tomiappdevelopment.core.domain.modules

import java.time.ZonedDateTime
import kotlin.time.Duration

data class ActivityGoalsData(
    val id: String,
    val duration: Duration,
    val dateTime: ZonedDateTime,
    val distance: Float,
    val mapPictureUrl: String?
)
