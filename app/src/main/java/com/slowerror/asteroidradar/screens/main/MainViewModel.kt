package com.slowerror.asteroidradar.screens.main

import android.app.Application
import androidx.lifecycle.*
import com.slowerror.asteroidradar.database.AsteroidsDatabase
import com.slowerror.asteroidradar.database.dao.AsteroidsDao
import com.slowerror.asteroidradar.database.entities.AsteroidEntity
import com.slowerror.asteroidradar.models.Asteroid
import com.slowerror.asteroidradar.models.PictureOfDay
import com.slowerror.asteroidradar.network.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private var _asteroids = MutableLiveData<List<Asteroid>>()
    val asteroids: LiveData<List<Asteroid>>
        get() = _asteroids

    private var _pictureOfDay = MutableLiveData<PictureOfDay>()
    val pictureOfDay: LiveData<PictureOfDay>
        get() = _pictureOfDay

    private var asteroidsList = listOf<Asteroid>(
        /*Asteroid(
            1,
            "name1",
            "1",
            1.0,
            1.0,
            1.0,
            1.0,
            true
        ),
        Asteroid(
            2,
            "name2",
            "2",
            2.0,
            2.0,
            2.0,
            2.0,
            false
        )*/
    )

    private val asteroidsDao: AsteroidsDao by lazy {
        AsteroidsDatabase.getInstance(application).asteroidsDao
    }

    private val asteroidApi = NetworkModule.api

    init {

        viewModelScope.launch {
            _pictureOfDay.value = getPicture()

            _asteroids.value = getAsteroids()

        }
    }

    private suspend fun getAsteroids(): List<Asteroid> = withContext(Dispatchers.IO) {
         try {
             val response = asteroidApi.getAsteroids(getToday(), getEndDay())

             asteroidsList = parseJsonResultToAsteroids(JSONObject(response))

             asteroidsDao.insertAsteroids(AsteroidEntity.asteroidListToAsteroidEntity(asteroidsList))
             asteroidsDao.getAllAsteroids()
         } catch (e: Exception) {
             e.printStackTrace()
             asteroidsDao.getAllAsteroids()
         }
    }

    private suspend fun getPicture(): PictureOfDay? = withContext(Dispatchers.IO) {
        try {
            val picture = asteroidApi.getPictureOfDay()
            picture
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }


}