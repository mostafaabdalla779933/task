package com.example.task.ui.favorite.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.task.data.model.ArticlesItem
import com.example.task.databinding.FragmentFavoriteBinding
import com.example.task.ui.favorite.state.FavoriteState
import com.example.task.ui.search.ArticlesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    private lateinit var binding:FragmentFavoriteBinding
    private lateinit var viewModel: FavoriteVM
    private val adapter : ArticlesAdapter by lazy {
        ArticlesAdapter(onclick =  {

        }, showSave = false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[FavoriteVM::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvArticles.adapter = adapter
        lifecycleScope.launch {
            viewModel.state.collect{
                handleState(it)
            }
        }
        viewModel.getFavoriteNews()
    }

    private fun handleState(state: FavoriteState) {
        binding.apply {
            when {
                state.isLoading -> {
                    adapter.setArticlesList((1..5).map { ArticlesItem(isShimmer = true) })
                }
                !state.isLoading -> {
                    adapter.setArticlesList(state.articles)
                }
            }
        }
    }






}