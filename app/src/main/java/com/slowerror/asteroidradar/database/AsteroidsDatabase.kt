package com.slowerror.asteroidradar.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.slowerror.asteroidradar.database.dao.AsteroidsDao
import com.slowerror.asteroidradar.database.entities.AsteroidEntity

@Database(version = 1, entities = [AsteroidEntity::class], exportSchema = false)
abstract class AsteroidsDatabase : RoomDatabase() {

    abstract val asteroidsDao: AsteroidsDao

    companion object {
        @Volatile
        private var INSTANCE: AsteroidsDatabase? = null

        fun getInstance(context: Context): AsteroidsDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AsteroidsDatabase::class.java,
                        "asteroids_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }

                return instance
            }
        }
    }


}