package com.slowerror.asteroidradar.screens.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.slowerror.asteroidradar.models.Asteroid
import com.slowerror.asteroidradar.models.PictureOfDay
import com.slowerror.asteroidradar.network.AsteroidApiService
import com.slowerror.asteroidradar.network.NetworkModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel : ViewModel() {

    private var _asteroids = MutableLiveData<List<Asteroid>>()
    val asteroids: LiveData<List<Asteroid>>
        get() = _asteroids

    private var _pictureOfDay = MutableLiveData<PictureOfDay>()
    val pictureOfDay: LiveData<PictureOfDay>
        get() = _pictureOfDay

    private var asteroidsList = listOf<Asteroid>(
        Asteroid(
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
        )
    )

    private val asteroidApi = NetworkModule.api

    init {
        _asteroids.value = asteroidsList

        viewModelScope.launch {
            _pictureOfDay.value = getPicture()
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