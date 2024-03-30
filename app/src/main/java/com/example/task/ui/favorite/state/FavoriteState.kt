package com.example.task.ui.favorite.state

import com.example.task.data.model.ArticlesItem

class FavoriteState(
    val isLoading: Boolean = true,
    val articles: List<ArticlesItem?> = emptyList()
)