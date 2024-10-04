package com.example.registration.di

import com.example.registration.data.SharedPrefRepositoryImpl
import com.example.registration.domain.repository.SharedPrefRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun bindSharedPrefRepository(
        sharedPrefRepositoryImpl: SharedPrefRepositoryImpl
    ): SharedPrefRepository
}