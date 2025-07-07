package com.mindorks.framework.mvvm.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mindorks.framework.mvvm.data.local.database.entity.Option
import kotlinx.coroutines.flow.Flow

@Dao
interface OptionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOption(option: Option)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOptions(options: List<Option>)

    @Query("SELECT * FROM options WHERE question_id = :questionId")
    fun getOptionsForQuestion(questionId: Long): Flow<List<Option>>

    @Query("SELECT * FROM options")
    fun getAllOptions(): Flow<List<Option>>

    @Query("SELECT COUNT(*) FROM options")
    suspend fun getOptionCount(): Int

    @Query("DELETE FROM options WHERE question_id = :questionId")
    suspend fun deleteOptionsForQuestion(questionId: Long)

    @Query("DELETE FROM options")
    suspend fun deleteAllOptions()
}