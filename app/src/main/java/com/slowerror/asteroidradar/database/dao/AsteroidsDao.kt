package com.slowerror.asteroidradar.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.slowerror.asteroidradar.database.entities.AsteroidEntity
import com.slowerror.asteroidradar.models.Asteroid

@Dao
interface AsteroidsDao {

    @Query("SELECT * FROM asteroids ORDER BY closeApproachData")
    fun getAllAsteroids(): LiveData<List<Asteroid>>

    @Query("SELECT * FROM asteroids WHERE closeApproachData = :today ORDER BY closeApproachData")
    fun getAsteroidsToday(today: String): LiveData<List<Asteroid>>

    @Query("SELECT * FROM asteroids WHERE closeApproachData >= :starDay and closeApproachData <= :endDay ORDER BY closeApproachData")
    fun getAsteroidsFromWeek(starDay: String, endDay: String): LiveData<List<Asteroid>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAsteroids(asteroid: List<AsteroidEntity>)

    @Query("DELETE FROM asteroids WHERE closeApproachData < :today")
    suspend fun deleteAsteroidsPreviousToday(today: String)
}