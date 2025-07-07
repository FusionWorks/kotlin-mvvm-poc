package com.mindorks.framework.mvvm.ui.main

import androidx.lifecycle.viewModelScope
import com.mindorks.framework.mvvm.data.model.QuestionWithOptions
import com.mindorks.framework.mvvm.data.repository.DataRepository
import com.mindorks.framework.mvvm.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    dataRepository: DataRepository
) : BaseViewModel(dataRepository) {

    private val _questions = MutableStateFlow<List<QuestionWithOptions>>(emptyList())
    val questions: StateFlow<List<QuestionWithOptions>> = _questions

    private val _userName = MutableStateFlow<String?>(null)
    val userName: StateFlow<String?> = _userName

    private val _userEmail = MutableStateFlow<String?>(null)
    val userEmail: StateFlow<String?> = _userEmail

    private val _userProfilePic = MutableStateFlow<String?>(null)
    val userProfilePic: StateFlow<String?> = _userProfilePic

    init {
        loadUserInfo()
        seedDatabaseIfNeeded()
        loadQuestions()
    }

    private fun loadUserInfo() {
        _userName.value = dataRepository.getCurrentUserName()
        _userEmail.value = dataRepository.getCurrentUserEmail()
        _userProfilePic.value = dataRepository.getCurrentUserProfilePicUrl()
    }

    private fun seedDatabaseIfNeeded() {
        viewModelScope.launch(exceptionHandler) {
            dataRepository.seedDatabaseQuestions()
            dataRepository.seedDatabaseOptions()
        }
    }

    private fun loadQuestions() {
        viewModelScope.launch(exceptionHandler) {
            dataRepository.getAllQuestionsWithOptions()
                .catch { throwable ->
                    error.value = throwable.message ?: "Error loading questions"
                }
                .collect { questionList ->
                    _questions.value = questionList
                }
        }
    }

    fun onCardSwiped(position: Int) {
        val currentList = _questions.value.toMutableList()
        if (position >= 0 && position < currentList.size) {
            currentList.removeAt(position)
            _questions.value = currentList
            
            // Reload questions if all cards are exhausted
            if (currentList.isEmpty()) {
                loadQuestions()
            }
        }
    }

    fun onOptionClicked(questionPosition: Int, optionIndex: Int) {
        // Handle option click logic here
        // You can add analytics, scoring, etc.
    }

    fun onLogoutClick() {
        launchWithLoading {
            val result = dataRepository.doLogoutApiCall()
            handleNetworkResult(
                result = result,
                onSuccess = {
                    dataRepository.setUserAsLoggedOut()
                    // Navigate to login will be handled by the activity
                },
                onError = { message, _ ->
                    // Even if logout API fails, we should log out locally
                    dataRepository.setUserAsLoggedOut()
                }
            )
        }
    }

    fun refreshQuestions() {
        loadQuestions()
    }
}