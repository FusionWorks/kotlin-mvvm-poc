package com.mindorks.framework.mvvm.data.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "questions")
data class Question(
    @PrimaryKey
    val id: Long,
    
    @ColumnInfo(name = "question_text")
    val questionText: String,
    
    @ColumnInfo(name = "question_img_url")
    val imgUrl: String? = null,
    
    @ColumnInfo(name = "created_at")
    val createdAt: String,
    
    @ColumnInfo(name = "updated_at")
    val updatedAt: String
)