package com.example.photoview.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.photoview.api.DTOs.search.PhotoDTO
import com.example.photoview.view.common.LoadingView

@Composable
fun ColumnScope.PagingPhotoView(
    setLoading: (Boolean) -> Unit,
    loading: Boolean,
    listState: LazyListState,
    onPhoto: (PhotoDTO) -> Unit,
    onUser: (PhotoDTO) -> Unit,
    viewModel: PagingViewModel,
) {
    val photos by rememberUpdatedState(newValue = viewModel.uiState.collectAsLazyPagingItems())

    Box(
        modifier = Modifier.weight(1f)
    ) {
        LazyColumn(
            Modifier
                .fillMaxSize()
                .padding(start = 8.dp, end = 8.dp),
            state = listState,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            setLoading(photos.loadState.refresh == LoadState.Loading)

            items(photos.itemCount) { index ->
                val photo = photos[index]
                if (photo != null) {
                    PhotoItem(
                        photo = photo,
                        onClick = { onPhoto(photo) },
                        onUser = { onUser(photo) },
                        loading
                    )
                }
            }
            if (photos.loadState.append == LoadState.Loading) {
                item {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(Alignment.CenterHorizontally)
                    )
                }
            }
        }

        if (loading) {
            LoadingView()
        }
    }
}