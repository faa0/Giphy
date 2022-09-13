package com.fara.giphy.presentation.features.preview

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.fara.giphy.domain.interactors.GifInteractor
import com.fara.giphy.presentation.base.BaseViewModel
import com.fara.giphy.presentation.features.preview.model.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flattenConcat
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PreviewViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val interactor: GifInteractor
) : BaseViewModel() {

    private val searchQueryFlow = savedStateHandle.getStateFlow(SEARCH_QUERY_VALUE, "")
    private val searchQueryInputFlow = MutableStateFlow("")
    private val searchQueryInputDebouncesFlow = searchQueryInputFlow.debounce(SEARCH_TIMEOUT)

    val searchQuery
        get() = savedStateHandle[SEARCH_QUERY_VALUE] ?: ""

    init {
        viewModelScope.launch {
            searchQueryInputDebouncesFlow.collectLatest {
                savedStateHandle[SEARCH_QUERY_VALUE] = it
            }
        }
    }

    val gifsFlow = flowOf(
        searchQueryFlow.flatMapLatest {
            interactor.getPaging(it, true)
        }.map { pagingData ->
            pagingData.map { it.toUi() }
        }.cachedIn(viewModelScope)
    ).flattenConcat()

    fun performSearch(query: String) {
        searchQueryInputFlow.value = query
    }

    fun ignoreGif(id: String) {
        viewModelScope.launch {
            interactor.ignoreGif(id)
        }
    }

    companion object {
        const val SEARCH_QUERY_VALUE = "SEARCH_QUERY"
        const val SEARCH_TIMEOUT = 300L
    }
}
