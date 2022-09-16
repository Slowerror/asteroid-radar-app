package com.slowerror.asteroidradar.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Asteroid(
    val id: Int,
    val codename: String,
    val closeApproachData: String,
    val absoluteMagnitude: Double,
    val estimatedDiameter: Double,
    val relativeVelocity: Double,
    val missDistance: Double,
    val isPotentiallyHazardous: Boolean
) : Parcelable
