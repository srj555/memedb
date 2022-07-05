package com.srdroid.memedb.presentation.meme_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.srdroid.memedb.common.Resource
import com.srdroid.memedb.domain.use_case.GetMemeDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MemeDetailsViewModel @Inject constructor(private val memeDetailsUseCase: GetMemeDetailsUseCase) :
    ViewModel() {


    private val _memeDetails = MutableStateFlow(MemeDetailsState())
    val memeDetails: StateFlow<MemeDetailsState> = _memeDetails


    fun getMemeDetails(id: String) {
        memeDetailsUseCase(id).onEach {
            when (it) {
                is Resource.Loading -> {
                    _memeDetails.value = MemeDetailsState(isLoading = true)
                }
                is Resource.Error -> {
                    _memeDetails.value = MemeDetailsState(error = it.message ?: "")
                }
                is Resource.Success -> {
                    _memeDetails.value = MemeDetailsState(data = it.data?.first { meme ->
                        meme.id == id
                    })
                }
            }
        }.launchIn(viewModelScope)
    }


}