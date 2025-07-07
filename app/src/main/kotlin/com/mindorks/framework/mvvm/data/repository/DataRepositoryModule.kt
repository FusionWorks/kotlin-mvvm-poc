package com.mindorks.framework.mvvm.data.repository

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindDataRepository(appDataRepository: AppDataRepository): DataRepository
}