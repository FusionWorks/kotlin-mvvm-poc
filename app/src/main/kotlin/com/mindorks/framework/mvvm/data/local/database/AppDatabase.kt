package com.mindorks.framework.mvvm.data.local.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import com.mindorks.framework.mvvm.data.local.database.dao.OptionDao
import com.mindorks.framework.mvvm.data.local.database.dao.QuestionDao
import com.mindorks.framework.mvvm.data.local.database.dao.UserDao
import com.mindorks.framework.mvvm.data.local.database.entity.Option
import com.mindorks.framework.mvvm.data.local.database.entity.Question
import com.mindorks.framework.mvvm.data.local.database.entity.User

@Database(
    entities = [User::class, Question::class, Option::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun questionDao(): QuestionDao
    abstract fun optionDao(): OptionDao

    companion object {
        const val DB_NAME = "mindorks_mvvm.db"
    }
}