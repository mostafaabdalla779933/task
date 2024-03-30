package com.example.task.ui.search.state

import com.example.task.data.model.ArticlesItem

class SearchState(
    val isLoading: Boolean = false,
    val results: List<ArticlesItem?> = emptyList(),
    val error: String? = null
)