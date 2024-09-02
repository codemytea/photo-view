package com.example.photoview.api

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.photoview.api.DTOs.search.PhotoDTO
import com.example.photoview.view.search.SearchType
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.withContext

/**
 * Provides photos from the FlickrAPI in a paged format
 *
 * @param text The [String] to search
 * @param searchType The [SearchType] of the search request
 * @param onError
 * */
class FlickrPagingSource(
    val text: String,
    private val searchType: SearchType? = null,
    val onError: (Throwable) -> Unit
) : PagingSource<Int, PhotoDTO>() {

    /**
     * Gets the next page in the request.
     * */
    override fun getRefreshKey(state: PagingState<Int, PhotoDTO>): Int {
        return ((state.anchorPosition ?: 0) - state.config.initialLoadSize / 2).coerceAtLeast(0)
    }


    /**
     * Loads the images.
     * */
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PhotoDTO> =
        withContext(Dispatchers.IO) {
            try {
                val currentPage = params.key ?: 1

                val response = when (searchType) {
                    SearchType.TEXT -> RetrofitClient.flickrApi.search(text, null, null, currentPage)
                    SearchType.USER -> RetrofitClient.flickrApi.search(null, text, null, currentPage)
                    SearchType.TAG -> RetrofitClient.flickrApi.search(null, null, text, currentPage)
                    null -> RetrofitClient.flickrApi.search(text, null, null, currentPage)
                }

                LoadResult.Page(
                    data = response.photos.photo.also {
                        supervisorScope {
                            it.map {
                                async { it.prefetchImageBytes() }
                            }.awaitAll()
                        }
                    },
                    prevKey = if (currentPage == 1) null else currentPage - 1,
                    nextKey = if (currentPage < response.photos.pages) currentPage + 1 else null
                )
            } catch (e: Throwable) {
                e.printStackTrace()
                if (e !is CancellationException) {
                    onError(e)
                }

                // IOException for network failures.
                LoadResult.Error(e)
            }
        }
}