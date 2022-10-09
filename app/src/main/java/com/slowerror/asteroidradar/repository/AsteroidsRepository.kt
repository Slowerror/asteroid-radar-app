package com.slowerror.asteroidradar.repository

import com.slowerror.asteroidradar.database.AsteroidsDatabase
import com.slowerror.asteroidradar.database.entities.AsteroidEntity
import com.slowerror.asteroidradar.network.NetworkModule
import com.slowerror.asteroidradar.network.getEndDay
import com.slowerror.asteroidradar.network.getToday
import com.slowerror.asteroidradar.network.parseJsonResultToAsteroids
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.IOException


class AsteroidsRepository(private val database: AsteroidsDatabase) {

    private val asteroidApi = NetworkModule.api

    val todayAsteroids = database.asteroidsDao.getAsteroidsToday(getToday())
    val weekAsteroids = database.asteroidsDao.getAsteroidsFromWeek(getToday(), getEndDay())
    val savedAsteroids = database.asteroidsDao.getAllAsteroids()

    suspend fun refreshAsteroids() {
        withContext(Dispatchers.IO) {
            try {
                val response = asteroidApi.getAsteroids(getToday(), getEndDay())
                val asteroidsList = parseJsonResultToAsteroids(JSONObject(response))
                database.asteroidsDao.insertAsteroids(AsteroidEntity.asteroidListToAsteroidEntity(asteroidsList))
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    suspend fun removeAsteroids() {
        withContext(Dispatchers.IO) {
            database.asteroidsDao.deleteAsteroidsPreviousToday(getToday())
        }
    }
}