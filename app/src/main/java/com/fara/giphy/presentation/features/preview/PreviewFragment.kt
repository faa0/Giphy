package com.fara.giphy.presentation.features.preview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import com.fara.giphy.R
import com.fara.giphy.databinding.FragmentPreviewBinding
import com.fara.giphy.presentation.base.BaseFragment
import com.fara.giphy.presentation.features.preview.adapter.PreviewPagingAdapter
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PreviewFragment : BaseFragment<PreviewViewModel, FragmentPreviewBinding>() {

    override val viewModel: PreviewViewModel by viewModels()

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        FragmentPreviewBinding.inflate(inflater, container, false)

    private var adapter: PreviewPagingAdapter? = null

    private val errorSnackBar by lazy {
        Snackbar.make(
            requireContext(),
            binding.root,
            getString(R.string.general_error),
            Snackbar.LENGTH_SHORT
        )
    }

    override fun initialize() {
        initRecycler()
    }

    override fun setupSubscribers() {
        subscribeToGifs()
        subscribeToLoadState()
    }

    override fun setupListeners() {
        binding.layoutRefresh.setOnRefreshListener {
            adapter?.refresh()
        }

        binding.editTextSearch.doAfterTextChanged {
            viewModel.performSearch(it.toString())
        }
    }

    private fun subscribeToGifs() {
        observe(viewModel.gifsFlow) { gifs ->
            adapter?.submitData(gifs)
        }
    }

    private fun subscribeToLoadState() {
        observe(adapter?.loadStateFlow) { loadState ->
            when (loadState.refresh) {
                is LoadState.NotLoading -> {
                    binding.layoutRefresh.isRefreshing = false
                    errorSnackBar.dismiss()
                }
                LoadState.Loading -> {
                    showRefreshIfNotShown()
                    errorSnackBar.dismiss()
                }
                is LoadState.Error -> {
                    binding.layoutRefresh.isRefreshing = false
                    errorSnackBar.show()
                }
            }
        }
    }

    private fun navigateToDetail(position: Int) {
        navController.navigate(
            PreviewFragmentDirections.actionPreviewFragmentToDetailFragment(
                position,
                viewModel.searchQuery
            )
        )
    }

    private fun initRecycler() {
        adapter = PreviewPagingAdapter(this::navigateToDetail, viewModel::ignoreGif)
        binding.recyclerPreview.adapter = adapter
    }

    private fun showRefreshIfNotShown() {
        if (!binding.layoutRefresh.isRefreshing) {
            binding.layoutRefresh.isRefreshing = true
        }
    }
}
