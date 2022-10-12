package com.slowerror.asteroidradar.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.slowerror.asteroidradar.database.AsteroidsDatabase
import com.slowerror.asteroidradar.database.entities.AsteroidEntity
import com.slowerror.asteroidradar.models.PictureOfDay
import com.slowerror.asteroidradar.network.NetworkModule
import com.slowerror.asteroidradar.network.getEndDay
import com.slowerror.asteroidradar.network.getToday
import com.slowerror.asteroidradar.network.parseJsonResultToAsteroids
import kotlinx.coroutines.*
import org.json.JSONObject
import java.io.IOException

class AsteroidsRepository(private val database: AsteroidsDatabase) {

    private val asteroidApi = NetworkModule.api

    private val _pictureOfDay = MutableLiveData<PictureOfDay>()

    val pictureOfDay: LiveData<PictureOfDay>
        get() = _pictureOfDay

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

    fun getSavedAsteroids() =
     database.asteroidsDao.getAllAsteroids()


    fun getTodayAsteroids() =
     database.asteroidsDao.getAsteroidsToday(getToday())


    fun getWeekAsteroids() =
     database.asteroidsDao.getAsteroidsFromWeek(getToday(), getEndDay())


    suspend fun getPicture() {
        withContext(Dispatchers.IO) {
            try {
                val pictureOfDay = asteroidApi.getPictureOfDay()
                if (pictureOfDay.mediaType == "image") {
                    _pictureOfDay.postValue(pictureOfDay)
                } else {
                    _pictureOfDay.postValue(PictureOfDay("","", ""))
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _pictureOfDay.postValue(PictureOfDay("","", ""))
            }
        }
    }

}