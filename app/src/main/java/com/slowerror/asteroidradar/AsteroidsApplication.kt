package com.slowerror.asteroidradar

import android.app.Application
import androidx.work.*
import com.slowerror.asteroidradar.work.DeleteAsteroidsWorker
import com.slowerror.asteroidradar.work.RefreshAsteroidsWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class AsteroidsApplication : Application() {

    private val appScope = CoroutineScope(Dispatchers.Default)

    override fun onCreate() {
        super.onCreate()
        delayInit()
    }

    private fun delayInit() {
        appScope.launch {
            setupRecurringWork()
        }
    }

    private fun setupRecurringWork() {
        val constraints = Constraints.Builder()
            .setRequiresCharging(true)
            .setRequiresBatteryNotLow(true)
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .apply {
                setRequiresDeviceIdle(true)
            }
            .build()

        val repeatingRefreshRequest =
            PeriodicWorkRequestBuilder<RefreshAsteroidsWorker>(1, TimeUnit.DAYS)
                .setConstraints(constraints)
                .build()


        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            RefreshAsteroidsWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            repeatingRefreshRequest
        )

        val repeatingDeleteRequest =
            PeriodicWorkRequestBuilder<DeleteAsteroidsWorker>(1, TimeUnit.DAYS)
                .setConstraints(constraints)
                .build()

        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            DeleteAsteroidsWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            repeatingDeleteRequest
        )
    }
}