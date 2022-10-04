package com.slowerror.asteroidradar.screens.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.slowerror.asteroidradar.models.Asteroid

class MainViewModel : ViewModel() {

    private var _asteroids = MutableLiveData<List<Asteroid>>()
    val asteroids: LiveData<List<Asteroid>>
        get() = _asteroids

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


    init {
        _asteroids.value = asteroidsList
    }


}