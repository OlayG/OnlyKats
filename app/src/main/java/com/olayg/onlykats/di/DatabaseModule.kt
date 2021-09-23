package com.olayg.onlykats.di

import android.content.Context
import com.olayg.onlykats.repo.local.KatDatabase
import com.olayg.onlykats.repo.local.dao.BreedDao
import com.olayg.onlykats.repo.local.dao.KatDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule
{
    @Singleton
    @Provides
    fun providesKatDb(@ApplicationContext context: Context) : KatDatabase = KatDatabase.getInstance(context)

    @Provides
    fun provideKatDao(database: KatDatabase) : KatDao = database.katDao()

    @Provides
    fun providesBreedDao(database: KatDatabase) : BreedDao = database.BreedDao()
}