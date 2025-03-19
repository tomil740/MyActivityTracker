package com.tomiappdevelopment.run.location

import android.location.Location
import com.tomiappdevelopment.core.domain.location.LocationWithAltitude

fun Location.toLocationWithAltitude(): LocationWithAltitude {
    return LocationWithAltitude(
        location = com.tomiappdevelopment.core.domain.location.Location(
            lat = latitude,
            long = longitude
        ),
        altitude = altitude
    )
}