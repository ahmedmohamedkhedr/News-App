package com.example.newsapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.domain.entities.ArticleDataModel
import com.example.newsapp.databinding.FragmentArticleDetailsBinding
import com.example.newsapp.ui.epoxy.controllers.ArticleDetailsController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.coroutines.CoroutineContext


class ArticleDetailsFragment : Fragment() {
    private lateinit var ui: FragmentArticleDetailsBinding
    private val controller: ArticleDetailsController by lazy { ArticleDetailsController() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        ui = FragmentArticleDetailsBinding.inflate(LayoutInflater.from(requireContext()))
        return ui.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setControllerToRecyclerView()
        getArgs()
    }


    private fun getArgs() {
        arguments?.apply {
            val article = getSerializable(ARG_ARTICLE) as ArticleDataModel?
            controller.article = article
        }
    }


    private fun setControllerToRecyclerView() {
        ui.recyclerView.adapter = controller.adapter
    }

    companion object {
        private const val ARG_ARTICLE = "ARG_ARTICLE"
        fun newInstance(article: ArticleDataModel) = ArticleDetailsFragment().apply {
            arguments = Bundle().apply {
                putSerializable(ARG_ARTICLE, article)
            }
        }
    }
}