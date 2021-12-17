package com.example.newsapp.ui.epoxy.models

import android.annotation.SuppressLint
import com.example.domain.entities.ArticleDataModel
import com.example.newsapp.R
import com.example.newsapp.databinding.ArticleDetailsLayoutBinding
import com.example.newsapp.ui.epoxy.BaseEpoxyViewBindingModel
import com.example.newsapp.utils.loadImage

class ArticleDetailsModel(private val article: ArticleDataModel) :
    BaseEpoxyViewBindingModel<ArticleDetailsLayoutBinding>(
        R.layout.article_details_layout
    ) {

    @SuppressLint("SetTextI18n")
    override fun ArticleDetailsLayoutBinding.bind() {
        article.title?.let { title.text = it }
        article.urlToImage?.let { thumbnail.loadImage(it) }
        article.author?.let { author.text = "${root.context.getString(R.string.author)} $it" }
        article.description?.let { description.text = "${root.context.getString(R.string.description)} $it" }
        article.content?.let { content.text = it }
    }

}