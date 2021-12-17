package com.example.newsapp.ui.epoxy.models

import android.annotation.SuppressLint
import com.example.domain.entities.ArticleDataModel
import com.example.newsapp.R
import com.example.newsapp.databinding.ItemArticleLayoutBinding
import com.example.newsapp.ui.epoxy.BaseEpoxyViewBindingModel
import com.example.newsapp.utils.loadImage

class ArticleHeaderModel(
    val article: ArticleDataModel,
    val onClick: (article: ArticleDataModel) -> Unit
) :
    BaseEpoxyViewBindingModel<ItemArticleLayoutBinding>(R.layout.item_article_layout) {

    @SuppressLint("SetTextI18n")
    override fun ItemArticleLayoutBinding.bind() {
        article.title?.let {
            articleTitleTextView.text = it
        }
        article.author?.let {
            articleAuthor.text = "${root.context.getString(R.string.author)} $it"
        }
        article.description?.let {
            articleDesc.text = it
        }
        articleImageView.loadImage(article.urlToImage)

        articleCardView.setOnClickListener {
            onClick(article)
        }
    }
}