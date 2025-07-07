package com.mindorks.framework.mvvm.debug

import android.content.Context
import com.mindorks.framework.mvvm.data.local.database.AppDatabase
import com.mindorks.framework.mvvm.utils.AppLogger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

/**
 * Database inspection utilities for debug builds
 */
object DatabaseInspector {

    suspend fun exportDatabase(context: Context, database: AppDatabase): File? {
        return withContext(Dispatchers.IO) {
            try {
                val dbFile = context.getDatabasePath(AppDatabase.DB_NAME)
                val exportDir = File(context.getExternalFilesDir(null), "debug")
                if (!exportDir.exists()) {
                    exportDir.mkdirs()
                }
                
                val exportFile = File(exportDir, "exported_${AppDatabase.DB_NAME}")
                
                // Close database connections
                database.close()
                
                // Copy database file
                FileInputStream(dbFile).use { input ->
                    FileOutputStream(exportFile).use { output ->
                        input.copyTo(output)
                    }
                }
                
                AppLogger.d("Database exported to: ${exportFile.absolutePath}")
                exportFile
            } catch (e: Exception) {
                AppLogger.e("Failed to export database: ${e.message}")
                null
            }
        }
    }

    suspend fun getDatabaseInfo(database: AppDatabase): DatabaseInfo {
        return withContext(Dispatchers.IO) {
            try {
                val userCount = database.userDao().getAllUsers()
                val questionCount = database.questionDao().getQuestionCount()
                val optionCount = database.optionDao().getOptionCount()
                
                DatabaseInfo(
                    userCount = 0, // userCount would need to be converted to suspend function
                    questionCount = questionCount,
                    optionCount = optionCount
                )
            } catch (e: Exception) {
                AppLogger.e("Failed to get database info: ${e.message}")
                DatabaseInfo(0, 0, 0)
            }
        }
    }

    data class DatabaseInfo(
        val userCount: Int,
        val questionCount: Int,
        val optionCount: Int
    )
}