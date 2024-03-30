package com.example.task.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.task.common.handleError
import com.example.task.data.model.ArticlesItem
import com.example.task.domain.usecase.SearchUserCase
import com.example.task.ui.search.state.SearchState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SearchVM @Inject constructor(private val useCase: SearchUserCase) : ViewModel() {


    private var job : Job? = null
    val state = MutableStateFlow(SearchState())

    fun searchExpression(query: String) {
        viewModelScope.launch {
            useCase.searchExpression(query)
                .onStart {
                    state.value = SearchState(isLoading = true)
                }.onEach { response ->
                    if (response.isSuccessful) {
                        response.body()?.let {
                            state.value = SearchState(
                                isLoading = false,
                                results = it.articles ?: emptyList()
                            )
                        } ?: kotlin.run {
                            state.value = SearchState(
                                isLoading = false,
                                error = "Something Went Wrong"
                            )
                        }
                    }
                }.catch {
                    state.value =
                        SearchState(isLoading = false, error = handleError(it))
                }.launchIn(viewModelScope).also { job = it }
        }
    }

    fun clearError(){
        state.value =
            SearchState(isLoading = false, error = null)
    }

    fun addArticleToFavorite(article: ArticlesItem){
        viewModelScope.launch {
            useCase.addArticle(article)
        }
    }

    fun cancel() {
        job?.let{ job ->
            if (job.isActive)
                job.cancel()
        }
    }

}