package com.srdroid.memedb.presentation.meme_search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.srdroid.memedb.common.Resource
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


    private val _memeSearchList = MutableStateFlow(MemeSearchState())
    val memeSearchList: StateFlow<MemeSearchState> = _memeSearchList
    private lateinit var _memesList: List<MemeModel>

    fun getMemes() {
        getMemeUseCase().onStart {
            // Update Loading State
            _memeSearchList.value = MemeSearchState(isLoading = true)
        }.onEach {
            when (it) {
                is Resource.Success -> {
                    _memesList = it.data ?: listOf()
                    _memeSearchList.value = MemeSearchState(data = _memesList)
                }
                is Resource.Error -> {
                    _memeSearchList.value = MemeSearchState(error = it.message ?: "")
                }
                else -> _memeSearchList.value = MemeSearchState(error = it.message ?: "")
            }
        }.launchIn(viewModelScope)
    }

    fun filterMemes(s: String) {
        // filter data
        val filteredData = _memesList.filter {
            it.name.lowercase().contains(s.lowercase())
        }
        // update data
        _memeSearchList.value =
            MemeSearchState(data = filteredData)
    }


}