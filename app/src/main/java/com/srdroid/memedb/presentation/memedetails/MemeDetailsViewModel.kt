package com.srdroid.memedb.presentation.memedetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.srdroid.memedb.core.Resource
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
) :
    ViewModel() {


    private val _memeDetails = MutableStateFlow(MemeDetailsState())
    val memeDetails: StateFlow<MemeDetailsState> = _memeDetails


    fun getMemeDetails(id: String) {
        memeDetailsUseCase(id).onStart {
            _memeDetails.value = MemeDetailsState(isLoading = true)
        }
            .onEach {
                when (it) {
                    is Resource.Error -> {
                        _memeDetails.value =
                            MemeDetailsState(error = errorViewMapper.mapToOut(it.errorEntity))
                    }
                    is Resource.Success -> {
                        _memeDetails.value =
                            MemeDetailsState(data = it.data?.map { memeData ->
                                mapper.mapToOut(
                                    memeData
                                )
                            }
                                ?.first { meme ->
                                    meme.id == id
                                })
                    }
                    else -> _memeDetails.value =
                        MemeDetailsState(error = errorViewMapper.mapToOut(it.errorEntity))
                }
            }.launchIn(viewModelScope)
    }


}