package com.example.photoview.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.photoview.api.DTOs.PhotoDTO
import com.example.photoview.api.FlickrAPI
import com.example.photoview.api.FlickrPagingSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PhotoSearchViewModel : ViewModel() {

    fun getSearchResults(searchTerm: String, onError: (Throwable) -> Unit): Flow<PagingData<PhotoDTO>> =
        Pager(PagingConfig(pageSize = FlickrAPI.pageSize, prefetchDistance = 200, enablePlaceholders = false)) {
            FlickrPagingSource(searchTerm, onError)
        }.flow

    private val _uiState = MutableStateFlow<PagingData<PhotoDTO>>(PagingData.empty())
    val uiState: StateFlow<PagingData<PhotoDTO>> = _uiState.asStateFlow()

    fun onSearchClicked(searchTerm: String, onError: (Throwable)->Unit) {
        viewModelScope.launch {
            try {
                getSearchResults(searchTerm, onError).collectLatest { pagingData ->
                    _uiState.value = pagingData
                }
            } catch (e: Throwable) {
                onError(e)
            }
        }
    }

    fun onPhotoClicked(photo: PhotoDTO) {

    }
}