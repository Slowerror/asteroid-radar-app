package com.slowerror.asteroidradar.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.slowerror.asteroidradar.database.dao.AsteroidsDao
import com.slowerror.asteroidradar.database.entities.AsteroidEntity

@Database(version = 1, entities = [AsteroidEntity::class], exportSchema = false)
abstract class AsteroidsDatabase : RoomDatabase() {

    abstract fun getAsteroidsDao(): AsteroidsDao
}