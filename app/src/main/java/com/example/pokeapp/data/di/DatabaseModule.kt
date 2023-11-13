package com.example.pokeapp.data.di

import android.content.Context
import com.example.pokeapp.data.AppDatabase
import com.example.pokeapp.data.dao.QuestionDao
import com.example.pokeapp.data.dao.RegionDao
import com.example.pokeapp.data.dao.SolutionDao
import com.example.pokeapp.data.dao.TrainerDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getDatabase(context)
    }

    @Provides
    fun provideTrainerDao(appDatabase: AppDatabase): TrainerDao {
        return appDatabase.trainerDao()
    }

    @Provides
    fun provideRegionDao(appDatabase: AppDatabase): RegionDao {
        return appDatabase.regionDao()
    }

    @Provides
    fun provideQuestionDao(appDatabase: AppDatabase): QuestionDao {
        return appDatabase.questionDao()
    }

    @Provides
    fun provideSolutionDao(appDatabase: AppDatabase): SolutionDao {
        return appDatabase.solutionDao()
    }
}