package com.fara.giphy.presentation.features.detail

import androidx.lifecycle.SavedStateHandle
import androidx.paging.map
import com.fara.giphy.domain.interactors.GifInteractor
import com.fara.giphy.presentation.base.BaseViewModel
import com.fara.giphy.presentation.features.preview.model.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject


@HiltViewModel
class DetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    interactor: GifInteractor
) : BaseViewModel() {

    private val args by lazy { DetailFragmentArgs.fromSavedStateHandle(savedStateHandle) }

    val gifsFlow = interactor.getPaging(
        args.query,
        false
    ).map { pagingData ->
        pagingData.map { gifImage ->
            gifImage.toUi()
        }
    }
}
