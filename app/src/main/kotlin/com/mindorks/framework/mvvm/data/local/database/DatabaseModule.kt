package com.mindorks.framework.mvvm.data.local.database

import android.content.Context
import androidx.room.Room
import com.mindorks.framework.mvvm.data.local.database.dao.OptionDao
import com.mindorks.framework.mvvm.data.local.database.dao.QuestionDao
import com.mindorks.framework.mvvm.data.local.database.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            AppDatabase.DB_NAME
        )
        .fallbackToDestructiveMigration()
        .build()
    }

    @Provides
    fun provideUserDao(database: AppDatabase): UserDao = database.userDao()

    @Provides
    fun provideQuestionDao(database: AppDatabase): QuestionDao = database.questionDao()

    @Provides
    fun provideOptionDao(database: AppDatabase): OptionDao = database.optionDao()
}