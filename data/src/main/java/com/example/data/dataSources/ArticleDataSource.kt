package com.example.data.dataSources

import com.example.data.models.NetworkArticleModel
import kotlinx.coroutines.Deferred

interface ArticleDataSource {
   suspend fun getArticles(pageNumber:Int): NetworkArticleModel?
}