package com.mindorks.framework.mvvm.ui.main.rating

import androidx.lifecycle.viewModelScope
import com.mindorks.framework.mvvm.R
import com.mindorks.framework.mvvm.data.repository.DataRepository
import com.mindorks.framework.mvvm.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RatingViewModel @Inject constructor(
    dataRepository: DataRepository
) : BaseViewModel(dataRepository) {

    private val _uiState = MutableStateFlow<RatingUiState>(RatingUiState.Initial)
    val uiState: StateFlow<RatingUiState> = _uiState

    private var isRatingSecondaryActionShown = false

    fun onRatingSubmitted(rating: Float, message: String) {
        if (rating == 0f) {
            viewModelScope.launch {
                _error.value = "Please provide a rating"
            }
            return
        }

        if (!isRatingSecondaryActionShown) {
            isRatingSecondaryActionShown = true
            if (rating == 5f) {
                _uiState.value = RatingUiState.ShowPlayStoreRating
            } else {
                _uiState.value = RatingUiState.ShowRatingMessage
            }
            return
        }

        // Submit rating (mock implementation)
        _uiState.value = RatingUiState.ShowThankYou
    }

    fun onLaterClicked() {
        _uiState.value = RatingUiState.Dismiss
    }

    fun onPlayStoreRatingClicked() {
        _uiState.value = RatingUiState.OpenPlayStore
    }

    sealed class RatingUiState {
        object Initial : RatingUiState()
        object ShowPlayStoreRating : RatingUiState()
        object ShowRatingMessage : RatingUiState()
        object OpenPlayStore : RatingUiState()
        object Dismiss : RatingUiState()
        object ShowThankYou : RatingUiState()
    }
}