package com.srdroid.memedb.presentation.memesearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.srdroid.memedb.core.Resource
import com.srdroid.memedb.domain.usecases.GetMemeUseCase
import com.srdroid.memedb.presentation.mapper.ErrorViewMapper
import com.srdroid.memedb.presentation.mapper.MemeMapper
import com.srdroid.memedb.presentation.model.MemeItemUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class MemeSearchViewModel @Inject constructor(
    private val getMemeUseCase: GetMemeUseCase,
    private val mapper: MemeMapper,
    private val errorViewMapper: ErrorViewMapper
) : ViewModel() {

    private val _getMemesState = MutableStateFlow(MemeSearchState())
    val getMemesState: StateFlow<MemeSearchState> = _getMemesState
    private lateinit var _memesList: List<MemeItemUIState>

    fun getMemes() {
        getMemeUseCase().onStart {
            // Update Loading State
            _getMemesState.value = MemeSearchState(isLoading = true)
        }.onEach {
            when (it) {
                is Resource.Success -> {
                    _memesList = it.data?.map { memeData -> mapper.mapToOut(memeData) } ?: listOf()
                    _getMemesState.value = MemeSearchState(data = _memesList)
                }
                is Resource.Error -> {
                    _getMemesState.value =
                        MemeSearchState(error = errorViewMapper.mapToOut(it.errorEntity))
                }
                else -> _getMemesState.value =
                    MemeSearchState(error = errorViewMapper.mapToOut(it.errorEntity))
            }
        }.launchIn(viewModelScope)
    }

    fun filterMemes(s: String) {
        // filter data
        val filteredData = _memesList.filter {
            it.name.lowercase().contains(s.lowercase())
        }
        // update state
        _getMemesState.value =
            MemeSearchState(data = filteredData)
    }

}