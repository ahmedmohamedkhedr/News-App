package com.example.newsapp.ui.epoxy.controllers

import com.airbnb.epoxy.EpoxyController
import com.example.domain.entities.ArticleDataModel
import com.example.newsapp.ui.epoxy.models.ArticleDetailsModel

class ArticleDetailsController : EpoxyController() {
    var article: ArticleDataModel? = null
        set(value) {
            field = value
            requestModelBuild()
        }

    override fun buildModels() {
        article?.let { ArticleDetailsModel(it).id(it.id).addTo(this) }
    }
}