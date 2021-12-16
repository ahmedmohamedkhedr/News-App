package com.example.newsapp.utils

data class PageInfoModel(
    var pageNumber: Int,
    var hasNext: Boolean
) {

    companion object {
        fun empty(): PageInfoModel = PageInfoModel(pageNumber = 1, hasNext = true)
    }
}
