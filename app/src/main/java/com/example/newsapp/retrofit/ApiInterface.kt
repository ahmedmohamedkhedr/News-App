package com.example.newsapp.retrofit

import com.example.data.models.NetworkArticleModel
import com.example.newsapp.utils.Constants
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiInterface {
    @GET("everything?q=${Constants.q_PARAMETER_REQUEST}&apiKey=${Constants.API_KEY}")
    suspend fun getArticlesAsync(@Query("page") page: Int): NetworkArticleModel?
}