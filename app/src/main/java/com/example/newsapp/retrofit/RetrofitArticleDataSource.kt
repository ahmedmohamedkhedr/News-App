package com.example.newsapp.retrofit

import com.example.data.dataSources.ArticleDataSource
import com.example.data.models.NetworkArticleModel
import com.example.domain.entities.ArticleDataModel
import kotlinx.coroutines.Deferred

class RetrofitArticleDataSource(private val service: ApiInterface) : ArticleDataSource {

    override suspend fun getArticles(pageNumber: Int): NetworkArticleModel? =
        service.getArticlesAsync(pageNumber)
}