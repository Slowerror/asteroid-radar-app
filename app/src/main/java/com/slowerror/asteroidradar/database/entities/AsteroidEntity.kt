package com.slowerror.asteroidradar.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "asteroids")
data class AsteroidEntity(
    @PrimaryKey val id: Int,
    val codename: String,
    val closeApproachData: String,
    val absoluteMagnitude: Double,
    val estimatedDiameter: Double,
    val relativeVelocity: Double,
    val missDistance: Double,
    val isPotentiallyHazardous: Boolean
)