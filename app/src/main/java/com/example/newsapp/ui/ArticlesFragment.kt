package com.example.newsapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.newsapp.databinding.FragmentArticlesBinding
import com.example.newsapp.presentation.ArticlesFragmentViewModel
import com.example.domain.result.ResultModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class ArticlesFragment: Fragment(){
    private lateinit var ui: FragmentArticlesBinding
    private val articlesFragmentViewModel by viewModel<ArticlesFragmentViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        ui = FragmentArticlesBinding.inflate(LayoutInflater.from(requireContext()))
        return ui.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        articlesFragmentViewModel.loadArticles()
        handleViewModelStates()
    }

    private fun handleViewModelStates() {

        when (val state = articlesFragmentViewModel.articlesFlowObserver.value) {
            is ResultModel.Idle -> {
                Log.d("res", "")
            }

            is ResultModel.Loading -> {
                Log.d("res", "")
            }

            is ResultModel.Success -> {
                Log.d("res", state.data.toString())

            }

            is ResultModel.Error -> {
                Log.d("res", state.errMessage ?: "")
            }
        }
    }

    companion object {
        fun newInstance() =
            ArticlesFragment()
    }
}