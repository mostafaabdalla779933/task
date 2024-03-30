package com.example.task.data.remote


import com.example.task.common.API_KEY
import com.example.task.data.model.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("v2/everything")
    suspend fun getNews(
        @Query("q") query: String,
        @Query("from") fromDate: String = "",
        @Query("to") toDate: String = "",
        @Query("sortBy") sortBy: String = "popularity",
        @Query("apiKey") apiKey: String = API_KEY
    ): Response<NewsResponse>


}