package com.slowerror.asteroidradar.screens.main

import android.app.Application
import androidx.lifecycle.*
import com.slowerror.asteroidradar.database.AsteroidsDatabase
import com.slowerror.asteroidradar.models.Asteroid
import com.slowerror.asteroidradar.models.PictureOfDay
import com.slowerror.asteroidradar.network.NetworkModule
import com.slowerror.asteroidradar.repository.AsteroidsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainViewModel(app: Application) : AndroidViewModel(app) {

    private val asteroidApi = NetworkModule.api
    private val asteroidsRepository = AsteroidsRepository(AsteroidsDatabase.getInstance(app))

    private val weekAsteroids: LiveData<List<Asteroid>> = asteroidsRepository.weekAsteroids
    private val todayAsteroids: LiveData<List<Asteroid>> = asteroidsRepository.todayAsteroids
    private val savedAsteroids: LiveData<List<Asteroid>> = asteroidsRepository.savedAsteroids

    val displayAsteroids = MediatorLiveData<List<Asteroid>>()

    private var _pictureOfDay = MutableLiveData<PictureOfDay>()

    val pictureOfDay: LiveData<PictureOfDay>
        get() = _pictureOfDay

    init {
        showWeekAsteroids()
        viewModelScope.launch {
            _pictureOfDay.value = getPicture()
            asteroidsRepository.refreshAsteroids()

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


    fun showWeekAsteroids() {
        replaceDisplayAsteroids(weekAsteroids)
    }

    fun showTodayAsteroids() {
        replaceDisplayAsteroids(todayAsteroids)
    }

    fun showSavedAsteroids() {
        replaceDisplayAsteroids(savedAsteroids)
    }

    private fun replaceDisplayAsteroids(showAsteroids:LiveData<List<Asteroid>>){
        displayAsteroids.removeSource(weekAsteroids)
        displayAsteroids.removeSource(todayAsteroids)
        displayAsteroids.removeSource(savedAsteroids)
        displayAsteroids.addSource(showAsteroids){displayAsteroids.value = it}
    }
}

