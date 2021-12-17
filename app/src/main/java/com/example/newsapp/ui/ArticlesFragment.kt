package com.example.newsapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.newsapp.databinding.FragmentArticlesBinding
import com.example.newsapp.presentation.ArticlesFragmentViewModel
import com.example.domain.result.ResultModel
import com.example.newsapp.R
import com.example.newsapp.ui.epoxy.controllers.ArticlesController
import com.example.newsapp.utils.moveTo
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.paginate.Paginate
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlin.coroutines.CoroutineContext


class ArticlesFragment : Fragment(), CoroutineScope, SwipeRefreshLayout.OnRefreshListener {
    private lateinit var ui: FragmentArticlesBinding
    private val articlesFragmentViewModel by viewModel<ArticlesFragmentViewModel>()
    private val job = Job()
    override val coroutineContext: CoroutineContext = Dispatchers.Main + job

    private val controller: ArticlesController by lazy {
        ArticlesController {
            val destination = ArticleDetailsFragment.newInstance(it)
            requireActivity().moveTo(destination, R.id.container)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        ui = FragmentArticlesBinding.inflate(LayoutInflater.from(requireContext()))
        return ui.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupSwipeToRefresh()
        handleViewModelStates()
        loadArticlesWithPagination()
    }


    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    private fun handleViewModelStates() {
        launch(coroutineContext) {
            articlesFragmentViewModel.articlesFlowObserver.collect { resultModel ->
                when (resultModel) {
                    is ResultModel.Idle -> {
                        ui.articlesSwipeToRefresh.isRefreshing = false
                    }

                    is ResultModel.Loading -> {
                        ui.articlesSwipeToRefresh.isRefreshing = true
                    }

                    else -> {
                        controller.bind(resultModel)
                        articlesFragmentViewModel.articlesFlowObserver.emit(ResultModel.Idle())
                    }
                }
            }
        }
    }

    private fun setupRecyclerView() {
        ui.articlesRecyclerView.adapter = controller.adapter
    }

    private fun setupSwipeToRefresh() {
        with(ui.articlesSwipeToRefresh) {
            setOnRefreshListener(this@ArticlesFragment)
            setProgressViewEndTarget(false, 120)
        }

    }

    private fun loadArticlesWithPagination() {
        Paginate.with(ui.articlesRecyclerView, articlesFragmentViewModel)
            .setLoadingTriggerThreshold(2).addLoadingListItem(false)
            .build()

    }


    override fun onRefresh() {
        controller.idleController()
        articlesFragmentViewModel.resetPagination()
        loadArticlesWithPagination()
    }

    companion object {
        fun newInstance() =
            ArticlesFragment()
    }
}