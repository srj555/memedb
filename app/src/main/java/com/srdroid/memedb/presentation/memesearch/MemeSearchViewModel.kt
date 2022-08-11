package com.srdroid.memedb.presentation.memesearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.srdroid.memedb.core.Resource
import com.srdroid.memedb.domain.usecases.GetMemeUseCase
import com.srdroid.memedb.presentation.mapper.ErrorViewMapper
import com.srdroid.memedb.presentation.mapper.MemeMapper
import com.srdroid.memedb.presentation.model.MemeItemUIModel
import com.srdroid.memedb.presentation.model.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class MemeSearchViewModel @Inject constructor(
    private val getMemeUseCase: GetMemeUseCase,
    private val mapper: MemeMapper,
    private val errorViewMapper: ErrorViewMapper,
) : ViewModel() {

    var initialServiceInvoked: Boolean = false

    private val _getMemesUiState: MutableStateFlow<UiState<List<MemeItemUIModel>>> =
        MutableStateFlow(UiState())
    val getMemesUiState: StateFlow<UiState<List<MemeItemUIModel>>> = _getMemesUiState
    private lateinit var _memesList: List<MemeItemUIModel>

    fun getMemes() {
        getMemeUseCase().onEach {
            when (it) {
                is Resource.Loading -> {
                    _getMemesUiState.value = UiState(isLoading = true)
                }
                is Resource.Success -> {
                    _memesList =
                        it.data?.map { memeData -> mapper.mapToOut(memeData) } ?: listOf()
                    _getMemesUiState.value = UiState(data = _memesList)
                }
                is Resource.Error -> {
                    _getMemesUiState.value =
                        UiState(error = errorViewMapper.mapToOut(it.errorEntity))
                }
            }
        }.launchIn(viewModelScope)
    }

    fun filterMemes(s: String) {
        if (this::_memesList.isInitialized) {
            val filteredData = _memesList.filter {
                it.name.lowercase().contains(s.lowercase())
            }
            _getMemesUiState.update {
                it.copy(data = filteredData)
            }
        }
    }
}