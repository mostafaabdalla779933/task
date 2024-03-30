package com.example.task.domain.repo

import com.example.task.data.model.ArticlesItem
import com.example.task.data.model.NewsResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface IRepository {

    suspend fun getFavourite(): Flow<List<ArticlesItem>>

    suspend fun searchNews(query:String): Flow<Response<NewsResponse>>

    suspend fun addArticle(article: ArticlesItem)

}