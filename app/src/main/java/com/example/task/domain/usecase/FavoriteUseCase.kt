package com.example.task.domain.usecase


import com.example.task.data.model.ArticlesItem
import com.example.task.domain.repo.IRepository
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


@ViewModelScoped
class FavoriteUseCase @Inject constructor(val repository: IRepository)  {

    suspend fun getFavoriteArticles(): Flow<List<ArticlesItem>> {
        return repository.getFavourite()
    }

}