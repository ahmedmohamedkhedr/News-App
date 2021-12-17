package com.example.newsapp.ui.epoxy.models

import com.example.newsapp.R
import com.example.newsapp.databinding.ErrorLayoutBinding
import com.example.newsapp.ui.epoxy.BaseEpoxyViewBindingModel

class NetworkErrorModel(private val errMessage: String?) :
    BaseEpoxyViewBindingModel<ErrorLayoutBinding>(R.layout.error_layout) {

    override fun ErrorLayoutBinding.bind() {
        errMessage?.let {
            errTextView.text = it
        }
    }
}