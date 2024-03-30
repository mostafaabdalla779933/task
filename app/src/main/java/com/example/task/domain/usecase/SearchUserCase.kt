package com.example.task.domain.usecase

import com.example.task.data.model.ArticlesItem
import com.example.task.data.model.NewsResponse
import com.example.task.domain.repo.IRepository
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject


@ViewModelScoped
class SearchUserCase @Inject constructor(private val repository: IRepository)  {

    suspend fun searchExpression(query: String): Flow<Response<NewsResponse>> {
        return repository.searchNews(query)
    }

    suspend fun addArticle(article:ArticlesItem){
        repository.addArticle(article)
    }
}