package com.example.domain.entities

data class ArticleDataModel(
    val id: String,
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val title: String?,
    val url: String?,
    val urlToImage: String?
)
