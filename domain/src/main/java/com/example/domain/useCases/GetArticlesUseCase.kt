package com.example.domain.useCases

import com.example.domain.dataInterfaces.ArticleRepository
import com.example.domain.entities.ArticleDataModel
import com.example.domain.result.ResultModel

class GetArticlesUseCase(private val repository: ArticleRepository) {
    suspend operator fun invoke(pageNumber: Int): ResultModel<MutableList<ArticleDataModel>?> {
        return try {
            val data = repository.getArticles(pageNumber)
            ResultModel.Success(data)
        } catch (e: Exception) {
            ResultModel.Error(e.message)
        }
    }
}