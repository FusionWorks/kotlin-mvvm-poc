package com.mindorks.framework.mvvm.ui.main

import com.mindorks.framework.mvvm.data.local.database.entity.Option
import com.mindorks.framework.mvvm.data.local.database.entity.Question
import com.mindorks.framework.mvvm.data.model.QuestionWithOptions
import org.junit.Assert.assertEquals
import org.junit.Test

class QuestionAdapterTest {

    @Test
    fun `test question diff callback items same`() {
        val question1 = Question(
            id = 1L,
            questionText = "Test Question",
            imgUrl = null,
            createdAt = "2023-01-01",
            updatedAt = "2023-01-01"
        )

        val question2 = Question(
            id = 1L,
            questionText = "Modified Question",
            imgUrl = null,
            createdAt = "2023-01-01",
            updatedAt = "2023-01-02"
        )

        val options = listOf(
            Option(1L, "Option 1", 1L, false, "2023-01-01", "2023-01-01"),
            Option(2L, "Option 2", 1L, true, "2023-01-01", "2023-01-01"),
            Option(3L, "Option 3", 1L, false, "2023-01-01", "2023-01-01")
        )

        val questionWithOptions1 = QuestionWithOptions(question1, options)
        val questionWithOptions2 = QuestionWithOptions(question2, options)

        val diffCallback = QuestionCardAdapter.QuestionDiffCallback()

        // Items should be the same (same ID)
        assertEquals(true, diffCallback.areItemsTheSame(questionWithOptions1, questionWithOptions2))

        // Contents should be different (different question text and updated date)
        assertEquals(false, diffCallback.areContentsTheSame(questionWithOptions1, questionWithOptions2))
    }

    @Test
    fun `test question diff callback different items`() {
        val question1 = Question(
            id = 1L,
            questionText = "Test Question 1",
            imgUrl = null,
            createdAt = "2023-01-01",
            updatedAt = "2023-01-01"
        )

        val question2 = Question(
            id = 2L,
            questionText = "Test Question 2",
            imgUrl = null,
            createdAt = "2023-01-01",
            updatedAt = "2023-01-01"
        )

        val options = listOf(
            Option(1L, "Option 1", 1L, false, "2023-01-01", "2023-01-01"),
            Option(2L, "Option 2", 1L, true, "2023-01-01", "2023-01-01"),
            Option(3L, "Option 3", 1L, false, "2023-01-01", "2023-01-01")
        )

        val questionWithOptions1 = QuestionWithOptions(question1, options)
        val questionWithOptions2 = QuestionWithOptions(question2, options)

        val diffCallback = QuestionCardAdapter.QuestionDiffCallback()

        // Items should be different (different IDs)
        assertEquals(false, diffCallback.areItemsTheSame(questionWithOptions1, questionWithOptions2))
    }
}