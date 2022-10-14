package com.slowerror.asteroidradar.screens.main

import android.app.Application
import androidx.lifecycle.*
import com.slowerror.asteroidradar.database.AsteroidsDatabase
import com.slowerror.asteroidradar.models.Asteroid
import com.slowerror.asteroidradar.models.PictureOfDay
import com.slowerror.asteroidradar.repository.AsteroidsRepository
import kotlinx.coroutines.launch

enum class FilterAsteroids {
    WEEK,
    TODAY,
    SAVED
}

class MainViewModel(app: Application) : AndroidViewModel(app) {

    private val asteroidsRepository = AsteroidsRepository(AsteroidsDatabase.getInstance(app))

    private val _asteroids = MutableLiveData(FilterAsteroids.WEEK)
    val asteroids: LiveData<List<Asteroid>> = Transformations.switchMap(_asteroids) { filter ->
        switchAsteroidsList(filter)
    }

    val pictureOfDay: LiveData<PictureOfDay> = asteroidsRepository.pictureOfDay

    init {
        viewModelScope.launch {
            asteroidsRepository.getPicture()
            asteroidsRepository.refreshAsteroids()
        }
    }

    fun updateFilter(filter: FilterAsteroids) {
        _asteroids.value = filter
    }

    private fun switchAsteroidsList(filter: FilterAsteroids): LiveData<List<Asteroid>> {
        return when (filter) {
            FilterAsteroids.WEEK -> asteroidsRepository.getWeekAsteroids()
            FilterAsteroids.TODAY -> asteroidsRepository.getTodayAsteroids()
            else -> asteroidsRepository.getSavedAsteroids()
        }
    }

}

