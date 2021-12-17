package com.example.domain

import com.example.domain.dataInterfaces.ArticleRepository
import com.example.domain.entities.ArticleDataModel
import com.example.domain.result.ResultModel
import com.example.domain.useCases.GetArticlesUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class GetArticlesUseCaseTest {

    @Test
    fun `test getArticles() with non null articles from repository then return result model success`() {
        runBlocking {
            val mockRepo = object : ArticleRepository {
                override suspend fun getArticles(pageNumber: Int): MutableList<ArticleDataModel> =
                    mutableListOf(
                        ArticleDataModel(
                            id = "1",
                            "author1",
                            content = "content1",
                            description = "description1",
                            publishedAt = "1234",
                            title = "title1",
                            "url1",
                            "urlImage1"
                        )
                    )
            }

            val useCase = GetArticlesUseCase(mockRepo)
            val response = useCase(1)
            assertTrue(response is ResultModel.Success)
        }
    }

    @Test
    fun `test getArticles() with exception from repository then return result model error`() {
        runBlocking {
            val mockRepo = object : ArticleRepository {
                override suspend fun getArticles(pageNumber: Int): MutableList<ArticleDataModel>? {
                    throw Exception()
                }
            }
            val useCase = GetArticlesUseCase(mockRepo)
            val response = useCase(1)
            assertTrue(response is ResultModel.Error)
        }
    }

    @Test
    fun `test getArticles() with null data from repository expected null data`() {
        runBlocking {
            val mockRepo = object : ArticleRepository {
                override suspend fun getArticles(pageNumber: Int): MutableList<ArticleDataModel>? {
                    return null
                }
            }
            val useCase = GetArticlesUseCase(mockRepo)
            val response = useCase(1)
            if (response is ResultModel.Success) {
                val expected: MutableList<ArticleDataModel>? = null
                assertEquals(response.data, expected)
            } else {
                assertTrue(response is ResultModel.Error)
            }
        }
    }

    @Test
    fun `test getArticles() with non null data from repository then validate model data not null`() {
        runBlocking {
            val mockRepo = object : ArticleRepository {
                override suspend fun getArticles(pageNumber: Int): MutableList<ArticleDataModel>? {
                    return mutableListOf(
                        ArticleDataModel(
                            id = "1",
                            "author1",
                            content = "content1",
                            description = "description1",
                            publishedAt = "1234",
                            title = "title1",
                            "url1",
                            "urlImage1"
                        )
                    )
                }
            }
            val useCase = GetArticlesUseCase(mockRepo)
            val response = useCase(1)
            if (response is ResultModel.Success) {
                val expected = mockRepo.getArticles(1)
                assertTrue(response.data.isNullOrEmpty().not())
                assertEquals(response.data, expected)
            } else {
                assertTrue(response is ResultModel.Error)
            }
        }
    }
}