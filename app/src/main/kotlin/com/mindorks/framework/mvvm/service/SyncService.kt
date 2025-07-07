package com.mindorks.framework.mvvm.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import com.mindorks.framework.mvvm.data.repository.DataRepository
import com.mindorks.framework.mvvm.utils.AppLogger
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SyncService : Service() {

    companion object {
        private const val TAG = "SyncService"

        fun getStartIntent(context: Context): Intent {
            return Intent(context, SyncService::class.java)
        }

        fun start(context: Context) {
            val starter = Intent(context, SyncService::class.java)
            context.startService(starter)
        }

        fun stop(context: Context) {
            context.stopService(Intent(context, SyncService::class.java))
        }
    }

    @Inject
    lateinit var dataRepository: DataRepository

    private val serviceScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    override fun onCreate() {
        super.onCreate()
        AppLogger.d(TAG, "SyncService created")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        AppLogger.d(TAG, "SyncService started")
        
        serviceScope.launch {
            try {
                // Perform background synchronization tasks
                dataRepository.seedDatabaseQuestions()
                dataRepository.seedDatabaseOptions()
                AppLogger.d(TAG, "Database seeding completed")
            } catch (e: Exception) {
                AppLogger.e(TAG, "Error during sync: ${e.message}")
            } finally {
                stopSelf(startId)
            }
        }
        
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        AppLogger.d(TAG, "SyncService stopped")
        serviceScope.cancel()
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null
}