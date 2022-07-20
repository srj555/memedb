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
    private val errorViewMapper: ErrorViewMapper,
) : ViewModel() {

    // state of initial service call
    var initialServiceInvoked: Boolean = false

    // Mutable UI State
    private val _getMemesState = MutableStateFlow(MemeSearchState())

    // Immutable UI State
    val getMemesState: StateFlow<MemeSearchState> = _getMemesState

    // MemeList
    private lateinit var _memesList: List<MemeItemUIState>

    /**
     * Method to get Memes
     */
    fun getMemes() {
        getMemeUseCase().onStart {
            // On Start Initial State , Update Loading State
            _getMemesState.value = MemeSearchState(isLoading = true)
        }.onEach {
            when (it) {
                is Resource.Success -> {
                    // On success get Meme Model from and map to List of MemeUIState Object
                    _memesList =
                        it.data?.map { memeData -> mapper.mapToOut(memeData) } ?: listOf()
                    // Update Mutable State
                    _getMemesState.value = MemeSearchState(data = _memesList)
                }
                is Resource.Error -> {
                    // Map Error to Error View State
                    _getMemesState.value =
                        MemeSearchState(error = errorViewMapper.mapToOut(it.errorEntity))
                }
            }
        }.launchIn(viewModelScope)
    }

    /**
     * Method to Update result based on filtered list
     */
    fun filterMemes(s: String) {
        // filter data
        val filteredData = _memesList.filter {
            it.name.lowercase().contains(s.lowercase())
        }
        // update state
        _getMemesState.update {
            it.copy(data = filteredData)
        }
    }
}