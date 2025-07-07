package com.mindorks.framework.mvvm.debug

import androidx.lifecycle.viewModelScope
import com.mindorks.framework.mvvm.data.local.database.AppDatabase
import com.mindorks.framework.mvvm.data.repository.DataRepository
import com.mindorks.framework.mvvm.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DebugViewModel @Inject constructor(
    dataRepository: DataRepository,
    private val database: AppDatabase
) : BaseViewModel(dataRepository) {

    private val _databaseInfo = MutableStateFlow(DatabaseInspector.DatabaseInfo(0, 0, 0))
    val databaseInfo: StateFlow<DatabaseInspector.DatabaseInfo> = _databaseInfo

    private val _message = MutableSharedFlow<String>()
    val message: SharedFlow<String> = _message

    init {
        refreshDatabaseInfo()
    }

    fun refreshDatabaseInfo() {
        viewModelScope.launch(exceptionHandler) {
            val info = DatabaseInspector.getDatabaseInfo(database)
            _databaseInfo.value = info
        }
    }

    fun exportDatabase() {
        launchWithLoading {
            // Export database functionality would go here
            _message.emit("Database export functionality available in debug builds")
        }
    }

    fun clearDatabase() {
        launchWithLoading {
            // Clear all tables
            database.userDao().deleteAllUsers()
            database.questionDao().deleteAllQuestions()
            database.optionDao().deleteAllOptions()
            
            _message.emit("Database cleared successfully")
            refreshDatabaseInfo()
        }
    }

    fun clearMessage() {
        // Messages are handled by SharedFlow, no need to clear manually
    }
}