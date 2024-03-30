package com.example.task.data.repo


import com.example.task.data.local.ArticleDao
import com.example.task.data.model.ArticlesItem
import com.example.task.data.model.NewsResponse
import com.example.task.data.remote.ApiService
import com.example.task.di.IoDispatcher
import com.example.task.domain.repo.IRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import javax.inject.Inject

class Repository @Inject constructor(
    private val apiService: ApiService,
    private val dao: ArticleDao,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : IRepository {



    override suspend fun getFavourite(): Flow<List<ArticlesItem>> {
        return flow {
            emit(dao.getArticlesItem())
        }.flowOn(dispatcher)
    }



    override suspend fun searchNews(query: String): Flow<Response<NewsResponse>> {
        return flow {
            val response = apiService.getNews(query)
            emit(response)
        }.flowOn(dispatcher)
    }

    override suspend fun addArticle(article: ArticlesItem) {
        dao.addArticleItem(article)
    }



}