package com.slowerror.asteroidradar.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.slowerror.asteroidradar.models.Asteroid

@Entity(tableName = "asteroids")
data class AsteroidEntity(
    @PrimaryKey val id: Long,
    val codename: String,
    val closeApproachData: String,
    val absoluteMagnitude: Double,
    val estimatedDiameter: Double,
    val relativeVelocity: Double,
    val missDistance: Double,
    val isPotentiallyHazardous: Boolean
) {

    companion object {
        fun asteroidListToAsteroidEntity(asteroids: List<Asteroid>): List<AsteroidEntity> {
            val asteroidsList = mutableListOf<AsteroidEntity>()
            for (asteroid in asteroids) {
                asteroidsList.add(
                    AsteroidEntity(
                        id = asteroid.id,
                        codename = asteroid.codename,
                        closeApproachData = asteroid.closeApproachData,
                        absoluteMagnitude = asteroid.absoluteMagnitude,
                        estimatedDiameter = asteroid.estimatedDiameter,
                        relativeVelocity = asteroid.relativeVelocity,
                        missDistance = asteroid.missDistance,
                        isPotentiallyHazardous = asteroid.isPotentiallyHazardous
                    )
                )
            }
            return asteroidsList.toList()
        }
    }
}