package com.example.domain.dataInterfaces

import com.example.domain.entities.ArticleDataModel
import kotlinx.coroutines.flow.Flow

interface ArticleRepository {
   suspend fun getArticles(pageNumber:Int): MutableList<ArticleDataModel>?
}