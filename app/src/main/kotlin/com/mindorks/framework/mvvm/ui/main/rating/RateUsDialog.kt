package com.mindorks.framework.mvvm.ui.main.rating

import android.graphics.PorterDuff
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.mindorks.framework.mvvm.R
import com.mindorks.framework.mvvm.databinding.DialogRateUsBinding
import com.mindorks.framework.mvvm.utils.AppUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RateUsDialog : DialogFragment() {

    private var _binding: DialogRateUsBinding? = null
    private val binding get() = _binding!!
    
    private val viewModel: RatingViewModel by viewModels()

    companion object {
        private const val TAG = "RateUsDialog"
        
        fun newInstance(): RateUsDialog {
            return RateUsDialog()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogRateUsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        observeViewModel()
    }

    private fun setupView() {
        // Setup rating bar colors
        val stars = binding.ratingBarFeedback.progressDrawable as LayerDrawable
        stars.getDrawable(2).setColorFilter(
            ContextCompat.getColor(requireContext(), R.color.yellow),
            PorterDuff.Mode.SRC_ATOP
        )
        stars.getDrawable(0).setColorFilter(
            ContextCompat.getColor(requireContext(), R.color.shadow),
            PorterDuff.Mode.SRC_ATOP
        )
        stars.getDrawable(1).setColorFilter(
            ContextCompat.getColor(requireContext(), R.color.shadow),
            PorterDuff.Mode.SRC_ATOP
        )

        // Hide secondary views initially
        binding.viewRatingMessage.visibility = View.GONE
        binding.viewPlayStoreRating.visibility = View.GONE

        // Setup click listeners
        binding.btnSubmit.setOnClickListener {
            val rating = binding.ratingBarFeedback.rating
            val message = binding.etMessage.text.toString()
            viewModel.onRatingSubmitted(rating, message)
        }

        binding.btnLater.setOnClickListener {
            viewModel.onLaterClicked()
        }

        binding.btnRateOnPlayStore.setOnClickListener {
            viewModel.onPlayStoreRatingClicked()
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                when (state) {
                    is RatingViewModel.RatingUiState.Initial -> {
                        // Keep initial state
                    }
                    is RatingViewModel.RatingUiState.ShowPlayStoreRating -> {
                        binding.viewPlayStoreRating.visibility = View.VISIBLE
                        binding.btnSubmit.visibility = View.GONE
                        binding.ratingBarFeedback.isEnabled = false
                    }
                    is RatingViewModel.RatingUiState.ShowRatingMessage -> {
                        binding.viewRatingMessage.visibility = View.VISIBLE
                    }
                    is RatingViewModel.RatingUiState.OpenPlayStore -> {
                        AppUtils.openPlayStoreForApp(requireContext())
                        dismiss()
                    }
                    is RatingViewModel.RatingUiState.Dismiss -> {
                        dismiss()
                    }
                    is RatingViewModel.RatingUiState.ShowThankYou -> {
                        // Show thank you message and dismiss
                        // You can add a toast or snackbar here
                        dismiss()
                    }
                }
            }
        }

        lifecycleScope.launch {
            viewModel.error.collect { error ->
                error?.let {
                    // Handle error - could show a toast
                    viewModel.clearError()
                }
            }
        }
    }

    fun show(fragmentManager: FragmentManager) {
        show(fragmentManager, TAG)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}