package com.example.photoview.api

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.photoview.api.DTOs.PhotoDTO
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.withContext

class FlickrPagingSource(
    val query: String,
    val onError: (Throwable)->Unit
) : PagingSource<Int, PhotoDTO>() {


//todo where do I need to put this?2

//    val flow = Pager(
//        // Configure how data is loaded by passing additional properties to
//        // PagingConfig, such as prefetchDistance.
//        PagingConfig(pageSize = 100)
//    ) {
//        FlickrPagingSource(backend, query)
//    }.flow
//        .cachedIn(viewModelScope)


    override fun getRefreshKey(state: PagingState<Int, PhotoDTO>): Int? {
        return ( (state. anchorPosition ?: 0) - state.config.initialLoadSize / 2).coerceAtLeast(0)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PhotoDTO> = withContext(Dispatchers.IO){
        try {
            val currentPage = params.key ?: 1
            val response = RetrofitClient.flickrApi.search(query, currentPage)
            LoadResult.Page(
                data = response.photos.photo.also{
                    supervisorScope {
                        it.map{
                            async { it.prefetchImageBytes() }
                        }.awaitAll()
                    }
                },
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = if(currentPage < response.photos.pages) currentPage + 1 else null
            )
        } catch (e: Throwable) {
            e.printStackTrace()
            if(e !is CancellationException){
                onError(e)
            }

            // IOException for network failures.
            LoadResult.Error(e)
        }


    }
}