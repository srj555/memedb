package com.srdroid.memedb.presentation.memedetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.srdroid.memedb.common.Resource
import com.srdroid.memedb.domain.usecases.GetMemeDetailsUseCase
import com.srdroid.memedb.presentation.mapper.ErrorViewMapper
import com.srdroid.memedb.presentation.mapper.MemeMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class MemeDetailsViewModel @Inject constructor(
    private val memeDetailsUseCase: GetMemeDetailsUseCase,
    private val mapper: MemeMapper,
    private val errorViewMapper: ErrorViewMapper
) : ViewModel() {

    // Mutable State Flow
    private val _memeDetails = MutableStateFlow(MemeDetailsState())

    // Immutable State flow observed from UI
    val memeDetails: StateFlow<MemeDetailsState> = _memeDetails

    /**
     * Get Meme details
     */
    fun getMemeDetails(id: String) {
        memeDetailsUseCase(id).onStart {
            // On Start Initial State , Update Loading State
            _memeDetails.value = MemeDetailsState(isLoading = true)
        }
            .onEach {
                when (it) {
                    is Resource.Error -> {
                        // Error State mapper
                        _memeDetails.value =
                            MemeDetailsState(error = errorViewMapper.mapToOut(it.errorEntity))
                    }
                    is Resource.Success -> {
                        // On success get Meme Model from and map to List of MemeUIState Object
                        _memeDetails.value =
                            MemeDetailsState(data = it.data
                                // map to Meme Details UI object
                                ?.map { memeData ->
                                    mapper.mapToOut(
                                        memeData
                                    )
                                }
                                // get the first element that matches the detail id
                                ?.first { meme ->
                                    meme.id == id
                                })
                    }
                }
            }.launchIn(viewModelScope)
    }
}