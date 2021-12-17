package com.example.newsapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entities.ArticleDataModel
import com.example.domain.useCases.GetArticlesUseCase
import com.example.newsapp.utils.PageInfoModel
import com.example.domain.result.ResultModel
import com.paginate.Paginate
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class ArticlesFragmentViewModel(
    private val useCase: GetArticlesUseCase,
    private val dispatcher: CoroutineDispatcher
) : ViewModel(),
    Paginate.Callbacks {
    var pageInfo: PageInfoModel = PageInfoModel.empty()
    var isLoadingCheck: Boolean = false
    val job = Job()

    val articlesFlowObserver =
        MutableStateFlow<ResultModel<MutableList<ArticleDataModel>?>>(ResultModel.Idle())


    override fun onCleared() {
        super.onCleared()
        clear()
    }

    fun clear() {
        resetPagination()
        job.cancel()
    }

    private suspend fun getArticles(): ResultModel<MutableList<ArticleDataModel>?> =
        withContext(Dispatchers.IO) { useCase(pageInfo.pageNumber) }

    suspend fun emitLoadingState() {
        if (pageInfo.pageNumber == 1) {
            articlesFlowObserver.emit(ResultModel.Loading())
        }
    }

    fun resetPagination() {
        pageInfo = PageInfoModel.empty()
        isLoadingCheck = false
    }

    suspend fun onLoadArticles(result: ResultModel<MutableList<ArticleDataModel>?>) {
        articlesFlowObserver.emit(result)
        isLoadingCheck = false
        if (result is ResultModel.Success) {
            pageInfo.pageNumber = pageInfo.pageNumber.plus(1)
            pageInfo.hasNext = result.data.isNullOrEmpty().not()
        } else {
            pageInfo.hasNext = false
        }
    }

    /**
     * these functions belongs to Paginate library that I use
     * this library make pagination much easier
     **/
    override fun onLoadMore() {
        isLoadingCheck = true
        viewModelScope.launch(dispatcher + job) {
            emitLoadingState()
            val result = getArticles()
            onLoadArticles(result)
        }
    }

    override fun isLoading(): Boolean = isLoadingCheck

    override fun hasLoadedAllItems(): Boolean = !pageInfo.hasNext

}