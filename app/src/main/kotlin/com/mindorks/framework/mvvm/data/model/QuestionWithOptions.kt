package com.mindorks.framework.mvvm.data.model

import androidx.room.Embedded
import androidx.room.Relation
import com.mindorks.framework.mvvm.data.local.database.entity.Option
import com.mindorks.framework.mvvm.data.local.database.entity.Question

data class QuestionWithOptions(
    @Embedded val question: Question,
    @Relation(
        parentColumn = "id",
        entityColumn = "question_id"
    )
    val options: List<Option>
)