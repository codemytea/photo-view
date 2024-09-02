package com.example.photoview.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.photoview.api.DTOs.search.PhotoDTO
import com.example.photoview.api.FlickrAPI
import com.example.photoview.api.FlickrPagingSource
import com.example.photoview.view.search.SearchType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PagingViewModel : ViewModel() {

    fun getSearchResults(
        text: String, searchType: SearchType?, onError: (Throwable) -> Unit
    ): Flow<PagingData<PhotoDTO>> =
        Pager(
            PagingConfig(
                pageSize = FlickrAPI.pageSize,
                prefetchDistance = 100,
                enablePlaceholders = false
            )
        ) {
            FlickrPagingSource(text, searchType, onError)
        }.flow

    private val _uiState = MutableStateFlow<PagingData<PhotoDTO>>(PagingData.empty())
    val uiState: StateFlow<PagingData<PhotoDTO>> = _uiState.asStateFlow()

    fun onSearch(text: String, searchType : SearchType?, onError: (Throwable) -> Unit) {
        viewModelScope.launch {
            try {
                getSearchResults(text, searchType, onError).collectLatest { pagingData ->
                    _uiState.value = pagingData
                }
            } catch (e: Throwable) {
                onError(e)
            }
        }
    }
}