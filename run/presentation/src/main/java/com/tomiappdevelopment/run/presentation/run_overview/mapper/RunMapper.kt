package com.tomiappdevelopment.run.presentation.run_overview.mapper


import com.tomiappdevelopment.core.domain.run.Run
import com.tomiappdevelopment.presentation.ui.formatted
import com.tomiappdevelopment.presentation.ui.toFormattedDateTime
import com.tomiappdevelopment.presentation.ui.toFormattedKm
import com.tomiappdevelopment.presentation.ui.toFormattedKmh
import com.tomiappdevelopment.presentation.ui.toFormattedMeters
import com.tomiappdevelopment.presentation.ui.toFormattedPace
import com.tomiappdevelopment.run.presentation.run_overview.model.RunUi
import java.time.ZoneId

fun Run.toRunUi(): RunUi {
    val dateTimeInLocalTime = dateTimeUtc
        .withZoneSameInstant(ZoneId.systemDefault())

    val distanceKm = distanceMeters / 1000.0

    return RunUi(
        id = id!!,
        duration = duration.formatted(),
        dateTime = dateTimeInLocalTime.toFormattedDateTime(),
        distance = distanceKm.toFormattedKm(),
        avgSpeed = avgSpeedKmh.toFormattedKmh(),
        maxSpeed = maxSpeedKmh.toFormattedKmh(),
        pace = duration.toFormattedPace(distanceKm),
        totalElevation = totalElevationMeters.toFormattedMeters(),
        mapPictureUrl = mapPictureUrl
    )
}
