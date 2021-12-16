package com.example.newsapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entities.ArticleDataModel
import com.example.domain.useCases.GetArticlesUseCase
import com.example.newsapp.utils.PageInfoModel
import com.example.domain.result.ResultModel
import com.paginate.Paginate
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ArticlesFragmentViewModel(
    private val useCase: GetArticlesUseCase,
    private val dispatcher: CoroutineDispatcher
) : ViewModel(),
    Paginate.Callbacks {
    private var pageInfo: PageInfoModel = PageInfoModel.empty()
    private var isLoadingCheck: Boolean = false
    private val job = Job()

    val articlesFlowObserver =
        MutableStateFlow<ResultModel<MutableList<ArticleDataModel>?>>(ResultModel.Idle())


    fun loadArticles() {
        onLoadMore()
    }

    override fun onCleared() {
        super.onCleared()
        resetPagination()
        job.cancel()
    }

    override fun onLoadMore() {
        isLoadingCheck = true
        viewModelScope.launch(dispatcher + job) {
            if (pageInfo.pageNumber == 1) {
                articlesFlowObserver.emit(ResultModel.Loading())
            }
            val result = useCase(pageInfo.pageNumber)
            articlesFlowObserver.emit(result)
            isLoadingCheck = false
            if (result is ResultModel.Success) {
                pageInfo.pageNumber = pageInfo.pageNumber.plus(1)
                pageInfo.hasNext = result.data.isNullOrEmpty().not()
            }
        }
    }

    override fun isLoading(): Boolean = isLoadingCheck

    override fun hasLoadedAllItems(): Boolean = !pageInfo.hasNext

    fun resetPagination() {
        pageInfo = PageInfoModel.empty()
        isLoadingCheck = false
    }
}