package com.slowerror.asteroidradar.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.slowerror.asteroidradar.database.AsteroidsDatabase
import com.slowerror.asteroidradar.repository.AsteroidsRepository
import retrofit2.HttpException

class RefreshAsteroidsWorker(appContext: Context, params: WorkerParameters)
    : CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        val database = AsteroidsDatabase.getInstance(applicationContext)
        val repository = AsteroidsRepository(database)

        return try {
            repository.refreshAsteroids()
            Result.success()
        }   catch (e: HttpException) {
            Result.retry()
        }
    }

    companion object {
        const val WORK_NAME = "refreshAsteroidsWorker"
    }
}