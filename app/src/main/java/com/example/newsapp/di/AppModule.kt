package com.example.newsapp.di

import com.example.data.dataSources.ArticleDataSource
import com.example.data.repositories.ArticleRepositoryImp
import com.example.domain.dataInterfaces.ArticleRepository
import com.example.domain.useCases.GetArticlesUseCase
import com.example.newsapp.utils.Constants
import com.example.newsapp.retrofit.ApiInterface
import com.example.newsapp.retrofit.RetrofitArticleDataSource
import com.example.newsapp.presentation.ArticlesFragmentViewModel
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val KEY_MAIN_DISPATCHER = "main_dispatcher"

fun getRetrofit(): Retrofit {
    return Retrofit.Builder().baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .client(OkHttpClient())
        .build()
}

fun getApiInterface(retrofit: Retrofit): ApiInterface = retrofit.create(ApiInterface::class.java)

fun getMainDispatcher(): CoroutineDispatcher = Dispatchers.Main

val dataModule = module {
    single<Retrofit> {
        getRetrofit()
    }

    single<ApiInterface> {
        getApiInterface(get())
    }

    single<ArticleDataSource> {
        RetrofitArticleDataSource(get())
    }

    single<ArticleRepository> {
        ArticleRepositoryImp(get())
    }

    single<GetArticlesUseCase> {
        GetArticlesUseCase(get())
    }

    single<CoroutineDispatcher>(named(KEY_MAIN_DISPATCHER)) {
        getMainDispatcher()
    }

    viewModel { ArticlesFragmentViewModel(get(), get(named(KEY_MAIN_DISPATCHER))) }
}

