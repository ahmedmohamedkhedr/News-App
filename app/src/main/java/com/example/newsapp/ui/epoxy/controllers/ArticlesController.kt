package com.example.newsapp.ui.epoxy.controllers

import com.airbnb.epoxy.EpoxyController
import com.example.domain.entities.ArticleDataModel
import com.example.domain.result.ResultModel
import com.example.newsapp.ui.epoxy.models.ArticleHeaderModel
import com.example.newsapp.ui.epoxy.models.NetworkErrorModel
import java.util.*

class ArticlesController(private val onClick: (article: ArticleDataModel) -> Unit) :
    EpoxyController() {
    private var model: ResultModel<MutableList<ArticleDataModel>> =
        ResultModel.Idle<MutableList<ArticleDataModel>>()

    private fun setArticles(resultModel: ResultModel.Success<MutableList<ArticleDataModel>?>) {
        if (model is ResultModel.Success) {
            resultModel.data?.let { (model as ResultModel.Success).data.addAll(it) }
        } else {
            model = ResultModel.Success(resultModel.data ?: mutableListOf())
        }
    }

    fun bind(resultModel: ResultModel<MutableList<ArticleDataModel>?>) {
        when (resultModel) {
            is ResultModel.Success -> {
                setArticles(resultModel)
            }

            is ResultModel.Error -> {
                model = ResultModel.Error(resultModel.errMessage)
            }
        }
        requestModelBuild()
    }

    fun idleController() {
        model = ResultModel.Idle()
        requestModelBuild()
    }

    override fun buildModels() {
        when (model) {
            is ResultModel.Success -> {
                (model as ResultModel.Success).data.forEach {
                    ArticleHeaderModel(it, onClick).id(it.id).addTo(this)
                }
            }

            is ResultModel.Error -> {
                NetworkErrorModel((model as ResultModel.Error).errMessage).id(
                    UUID.randomUUID().toString()
                ).addTo(this)
            }
        }
    }
}