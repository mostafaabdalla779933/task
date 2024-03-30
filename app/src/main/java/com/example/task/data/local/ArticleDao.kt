package com.example.task.data.local

import androidx.room.*
import com.example.task.data.model.ArticlesItem


@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addArticleItem(article : ArticlesItem) : Long


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addArticleItems(article: List<ArticlesItem?>) : List<Long>

    @Query("SELECT * FROM article")
    suspend fun getArticlesItem(): List<ArticlesItem>

    @Query("DELETE FROM article WHERE roomId = :roomId")
    suspend fun deleteArticleItemById(roomId : Int) : Int

    @Delete
    suspend fun deleteArticlesItem(article : ArticlesItem) : Int

    @Query("DELETE FROM article")
    suspend fun clearCachedArticleItems(): Int
}