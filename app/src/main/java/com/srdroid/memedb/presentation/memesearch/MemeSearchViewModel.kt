package com.srdroid.memedb.presentation.memesearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.srdroid.memedb.core.Resource
import com.srdroid.memedb.domain.usecases.GetMemeUseCase
import com.srdroid.memedb.presentation.mapper.ErrorViewMapper
import com.srdroid.memedb.presentation.mapper.MemeMapper
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

    // Filter flow that holds updates for filter
    private val filterFlow = MutableStateFlow("")

    // Filter value
    var filter: String
        get() = filterFlow.value
        set(value) {
            filterFlow.value = value
        }

    // Mutable State Flow
    private val _getMemesState = MutableStateFlow(MemeSearchState())

    //Combine MemeSearchState with filtered result
    val getMemesState: StateFlow<MemeSearchState> =
        _getMemesState.combine(filterFlow) { memeSearchState, filter ->
            MemeSearchState(data = memeSearchState.data?.filter {
                it.name.lowercase().contains(filter.lowercase())
            })
        }.stateIn(viewModelScope, SharingStarted.Eagerly, MemeSearchState())

    fun getMemes() {
        getMemeUseCase().onStart {
            // Update Loading State
            _getMemesState.value = MemeSearchState(isLoading = true)
        }.onEach {
            when (it) {
                is Resource.Success -> {
                    val memesList =
                        it.data?.map { memeData -> mapper.mapToOut(memeData) } ?: listOf()
                    _getMemesState.value = MemeSearchState(data = memesList)
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
}