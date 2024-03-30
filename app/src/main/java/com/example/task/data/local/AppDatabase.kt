package com.example.task.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.task.data.model.ArticlesItem

@Database(entities = [ArticlesItem::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun articleDao() : ArticleDao
}