package com.slowerror.asteroidradar.screens.main

import android.app.Application
import androidx.lifecycle.*
import com.slowerror.asteroidradar.database.AsteroidsDatabase
import com.slowerror.asteroidradar.models.Asteroid
import com.slowerror.asteroidradar.network.NetworkModule
import com.slowerror.asteroidradar.repository.AsteroidsRepository
import kotlinx.coroutines.launch


class MainViewModel(app: Application) : AndroidViewModel(app) {

    private val asteroidApi = NetworkModule.api
    private val asteroidsRepository = AsteroidsRepository(AsteroidsDatabase.getInstance(app))

    private val weekAsteroids: LiveData<List<Asteroid>> = asteroidsRepository.weekAsteroids
    private val todayAsteroids: LiveData<List<Asteroid>> = asteroidsRepository.todayAsteroids
    private val savedAsteroids: LiveData<List<Asteroid>> = asteroidsRepository.savedAsteroids

    val displayAsteroids = MediatorLiveData<List<Asteroid>>()

    private val _pictureUrl = MutableLiveData<String>()
    val pictureUrl: LiveData<String>
        get() = _pictureUrl

    private val _pictureTitle = MutableLiveData<String>()
    val pictureTitle: LiveData<String>
        get() = _pictureTitle

    init {
        showWeekAsteroids()
        getPicture()
        viewModelScope.launch {
            asteroidsRepository.refreshAsteroids()
        }
    }

    private fun getPicture() {
        viewModelScope.launch {
            try {
                val pictureOfDay = asteroidApi.getPictureOfDay()
                if (pictureOfDay.mediaType == "image") {
                    _pictureUrl.value = pictureOfDay.url
                    _pictureTitle.value = pictureOfDay.title
                } else {
                    _pictureUrl.value = ""
                    _pictureTitle.value = ""
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _pictureUrl.value = ""
                _pictureTitle.value = ""
            }
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

