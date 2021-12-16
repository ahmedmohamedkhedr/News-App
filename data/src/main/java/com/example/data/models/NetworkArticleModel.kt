package com.example.data.models

import com.example.data.mapper.Mapper
import com.example.domain.entities.ArticleDataModel
import java.io.Serializable
import java.util.*

data class NetworkArticleModel(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>
) : Serializable

data class Article(
    val author: Any?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val source: Source?,
    val title: String?,
    val url: String?,
    val urlToImage: String?
) : Serializable, Mapper<Article, ArticleDataModel> {

    override fun map(from: Article): ArticleDataModel = ArticleDataModel(
        id = source?.id ?: UUID.randomUUID().toString(),
        author = author?.toString(),
        content = content,
        description = description,
        publishedAt = publishedAt,
        title = title,
        url = url,
        urlToImage = urlToImage
    )
}

data class Source(
    val id: String?,
    val name: String?
) : Serializable