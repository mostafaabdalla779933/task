package com.example.task.di

import com.example.task.data.local.ArticleDao
import com.example.task.data.remote.ApiService
import com.example.task.data.repo.Repository
import com.example.task.domain.repo.IRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {


    /**
     * Provides [Repository] instance
     */
    @Provides
    @Singleton
    fun provideRepository(apiService: ApiService, dao: ArticleDao, @IoDispatcher dispatcher: CoroutineDispatcher): IRepository {
        return Repository(apiService,dao,dispatcher)
    }
}