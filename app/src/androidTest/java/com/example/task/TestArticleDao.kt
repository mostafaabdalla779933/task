package com.example.task

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.task.data.local.AppDatabase
import com.example.task.data.local.ArticleDao
import com.example.task.data.model.ArticlesItem
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException



@RunWith(AndroidJUnit4::class)
class TestArticleDao {
    private lateinit var articleDao: ArticleDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java).allowMainThreadQueries().build()
        articleDao = db.articleDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }


    @Test
    fun insert_Article_And_Read_It_From_The_List() = runBlocking {
        val article =  ArticlesItem(title = "title", publishedAt = "publishedAt", content = "content")
        articleDao.addArticleItem(article)
        assert(articleDao.getArticlesItem().contains(article))
    }

    @Test
    fun insert_List_Of_Articles_And_Read_It_From_The_List() = runBlocking {

        val list = listOf(ArticlesItem(title = "title", publishedAt = "publishedAt", content = "content"),
            ArticlesItem(title = "title1", publishedAt = "publishedAt1", content = "content1"))
        articleDao.addArticleItems(list)
        articleDao.getArticlesItem().let{
            assert(it.containsAll(list))
        }
    }


    @Test
    fun clear_Table() = runBlocking {
        articleDao.clearCachedArticleItems()
        assert(articleDao.getArticlesItem().isEmpty())
    }

}