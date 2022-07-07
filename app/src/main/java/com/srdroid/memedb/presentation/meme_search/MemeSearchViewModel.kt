package com.srdroid.memedb.presentation.meme_search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.srdroid.memedb.core.Resource
import com.srdroid.memedb.domain.model.MemeModel
import com.srdroid.memedb.domain.use_case.GetMemeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class MemeSearchViewModel @Inject constructor(
    private val getMemeUseCase: GetMemeUseCase
) : ViewModel() {


    private val _getMemesState = MutableStateFlow(MemeSearchState())
    val getMemesState: StateFlow<MemeSearchState> = _getMemesState
    private lateinit var _memesList: List<MemeModel>

    fun getMemes() {
        getMemeUseCase().onStart {
            // Update Loading State
            _getMemesState.value = MemeSearchState(isLoading = true)
        }.onEach {
            when (it) {
                is Resource.Success -> {
                    _memesList = it.data ?: listOf()
                    _getMemesState.value = MemeSearchState(data = _memesList)
                }
                is Resource.Error -> {
                    _getMemesState.value = MemeSearchState(error = it.message ?: "")
                }
                else -> _getMemesState.value = MemeSearchState(error = it.message ?: "")
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