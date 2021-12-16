package com.example.data.repositories

import com.example.data.dataSources.ArticleDataSource
import com.example.domain.dataInterfaces.ArticleRepository
import com.example.domain.entities.ArticleDataModel


class ArticleRepositoryImp(private val articleDataSource: ArticleDataSource) : ArticleRepository {
    override suspend fun getArticles(pageNumber: Int): MutableList<ArticleDataModel>? =
        articleDataSource.getArticles(pageNumber)?.articles?.map { it.map(it) }
            ?.toMutableList()
}