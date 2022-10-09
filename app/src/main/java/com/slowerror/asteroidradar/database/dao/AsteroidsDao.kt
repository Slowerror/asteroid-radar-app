package com.slowerror.asteroidradar.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.slowerror.asteroidradar.database.entities.AsteroidEntity
import com.slowerror.asteroidradar.models.Asteroid

@Dao
interface AsteroidsDao {

    @Query("SELECT * FROM asteroids ORDER BY closeApproachData")
    suspend fun getAllAsteroids(): List<Asteroid>

    @Query("SELECT * FROM asteroids WHERE closeApproachData = :today ORDER BY closeApproachData")
    suspend fun getAsteroidsToday(today: String): List<Asteroid>

    @Query("SELECT * FROM asteroids WHERE closeApproachData >= :starDay and closeApproachData <= :endDay ORDER BY closeApproachData")
    suspend fun getAsteroidsFromWeek(starDay: String, endDay: String): List<Asteroid>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAsteroids(asteroid: List<AsteroidEntity>)
}