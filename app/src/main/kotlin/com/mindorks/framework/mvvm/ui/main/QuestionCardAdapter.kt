package com.mindorks.framework.mvvm.ui.main

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.mindorks.framework.mvvm.data.model.QuestionWithOptions
import com.mindorks.framework.mvvm.databinding.CardLayoutBinding
import com.mindorks.framework.mvvm.ui.base.BaseAdapter

/**
 * Adapter for displaying question cards in RecyclerView
 */
class QuestionCardAdapter(
    private val onOptionClick: (Int, Int) -> Unit
) : BaseAdapter<QuestionWithOptions, QuestionCardAdapter.QuestionViewHolder>(QuestionDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val binding = CardLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return QuestionViewHolder(binding, onOptionClick)
    }

    class QuestionViewHolder(
        binding: CardLayoutBinding,
        private val onOptionClick: (Int, Int) -> Unit
    ) : BaseViewHolder<QuestionWithOptions, CardLayoutBinding>(binding) {

        override fun bind(item: QuestionWithOptions) {
            with(binding) {
                tvQuestionTxt.text = item.question.questionText

                // Load image if available
                item.question.imgUrl?.let { imageUrl ->
                    Glide.with(ivPic.context)
                        .load(imageUrl)
                        .into(ivPic)
                }

                // Set up options (assuming 3 options)
                if (item.options.size >= 3) {
                    btnOption1.text = item.options[0].optionText
                    btnOption2.text = item.options[1].optionText
                    btnOption3.text = item.options[2].optionText

                    btnOption1.setOnClickListener {
                        showCorrectOptions(item)
                        onOptionClick(adapterPosition, 0)
                    }

                    btnOption2.setOnClickListener {
                        showCorrectOptions(item)
                        onOptionClick(adapterPosition, 1)
                    }

                    btnOption3.setOnClickListener {
                        showCorrectOptions(item)
                        onOptionClick(adapterPosition, 2)
                    }
                }
            }
        }

        private fun showCorrectOptions(item: QuestionWithOptions) {
            with(binding) {
                item.options.forEachIndexed { index, option ->
                    val button = when (index) {
                        0 -> btnOption1
                        1 -> btnOption2
                        2 -> btnOption3
                        else -> return@forEachIndexed
                    }
                    
                    button.setBackgroundColor(
                        if (option.isCorrect) Color.GREEN else Color.RED
                    )
                }
            }
        }
    }

    private class QuestionDiffCallback : DiffUtil.ItemCallback<QuestionWithOptions>() {
        override fun areItemsTheSame(
            oldItem: QuestionWithOptions,
            newItem: QuestionWithOptions
        ): Boolean {
            return oldItem.question.id == newItem.question.id
        }

        override fun areContentsTheSame(
            oldItem: QuestionWithOptions,
            newItem: QuestionWithOptions
        ): Boolean {
            return oldItem == newItem
        }
    }
}