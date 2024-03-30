package com.example.task.ui.search

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.task.common.showMessage
import com.example.task.data.model.ArticlesItem
import com.example.task.databinding.FragmentSearchBinding
import com.example.task.ui.search.state.SearchState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var viewModel: SearchVM
    private val resultAdapter: ArticlesAdapter by lazy {
        ArticlesAdapter(onclick =  {
            viewModel.addArticleToFavorite(it)
            showMessage("saved")
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[SearchVM::class.java]
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        lifecycleScope.launch {
            viewModel.state.collect {
                handleState(it)
            }
        }

    }

    private fun handleState(state: SearchState) {
        when {
            state.isLoading -> {
                resultAdapter.setArticlesList((1..5).map { ArticlesItem(isShimmer = true) })
            }

            state.error != null -> {
                showMessage(state.error)
                resultAdapter.setArticlesList(emptyList())
                viewModel.clearError()
            }

            else -> {
                resultAdapter.setArticlesList(state.results)
            }
        }
    }

    private fun initView() {
        binding.apply {
            sv.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if(query.isNullOrEmpty().not()){
                        viewModel.cancel()
                        viewModel.searchExpression(query?.trim() ?: "")
                        hideKeyboard()
                    }
                    return true
                }
                override fun onQueryTextChange(newText: String?): Boolean {
                    return true
                }
            })

            rvSearchResult.adapter = resultAdapter
        }
    }

    fun hideKeyboard(){
        try {
            val inputMethodManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            if (inputMethodManager.isAcceptingText) {
                inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
            }
        }catch (e:Exception){}

    }
}