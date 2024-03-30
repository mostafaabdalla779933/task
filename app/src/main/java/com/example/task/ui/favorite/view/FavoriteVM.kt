package com.example.task.ui.favorite.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.task.domain.usecase.FavoriteUseCase
import com.example.task.ui.favorite.state.FavoriteState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FavoriteVM @Inject constructor(private val useCase: FavoriteUseCase) : ViewModel() {

    val state = MutableStateFlow<FavoriteState>(FavoriteState())

    fun getFavoriteNews() {
        viewModelScope.launch {
            useCase.getFavoriteArticles()
                .onStart {
                    state.value = FavoriteState(isLoading = true)
                }.onEach { list ->
                    state.value = FavoriteState(
                        isLoading = false,
                        articles = list
                    )
                }.catch {
                    state.value = FavoriteState(
                        isLoading = false,
                        articles = emptyList()
                    )
                }.launchIn(viewModelScope)
        }
    }


}