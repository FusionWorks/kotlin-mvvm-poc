package com.mindorks.framework.mvvm.ui.splash

import androidx.lifecycle.viewModelScope
import com.mindorks.framework.mvvm.data.model.LoggedInMode
import com.mindorks.framework.mvvm.data.repository.DataRepository
import com.mindorks.framework.mvvm.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    dataRepository: DataRepository
) : BaseViewModel(dataRepository) {

    private val _navigationEvent = MutableSharedFlow<NavigationEvent>()
    val navigationEvent: SharedFlow<NavigationEvent> = _navigationEvent

    init {
        initializeApp()
    }

    private fun initializeApp() {
        viewModelScope.launch(exceptionHandler) {
            // Seed database
            dataRepository.seedDatabaseQuestions()
            dataRepository.seedDatabaseOptions()
            
            // Add minimum splash delay for branding
            delay(2000)
            
            // Decide next screen based on login status
            val loginMode = dataRepository.getCurrentUserLoggedInMode()
            if (loginMode == LoggedInMode.LOGGED_OUT.type) {
                _navigationEvent.emit(NavigationEvent.NavigateToLogin)
            } else {
                _navigationEvent.emit(NavigationEvent.NavigateToMain)
            }
        }
    }

    sealed class NavigationEvent {
        object NavigateToLogin : NavigationEvent()
        object NavigateToMain : NavigationEvent()
    }
}