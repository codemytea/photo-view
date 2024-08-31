package com.example.photoview.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.photoview.api.DTOs.PhotoDTO
import com.example.photoview.utils.imageBitmap
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoViewScreen(
    contents: @Composable ColumnScope.() -> Unit,
) {
    var text by rememberSaveable { mutableStateOf("Yorkshire") }
    var expanded by rememberSaveable { mutableStateOf(false) }

    val viewModel: PhotoSearchViewModel = viewModel()
    val photos by rememberUpdatedState(newValue = viewModel.uiState.collectAsLazyPagingItems())

    val errorDialog = rememberDialog()

    var loading by rememberSaveable {
        mutableStateOf(false)
    }

    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(true) {
        viewModel.onSearchClicked("Yorkshire", errorDialog::genericError)
    }


    Surface {
        Column(
            modifier =
            Modifier
                .fillMaxWidth()
                .padding(top = 32.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {

            //todo expanded????
            DockedSearchBar(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .height(56.dp),
                inputField = {
                    SearchBarDefaults.InputField(
                        enabled = !loading,
                        query = text,
                        onQueryChange = { text = it },
                        onSearch = {
                            scope.launch {
                                listState.scrollToItem(0)
                            }
                            expanded = false
                            viewModel.onSearchClicked(it, errorDialog::genericError)
                        },
                        expanded = expanded,
                        onExpandedChange = { expanded = it },
                        placeholder = { Text("Search") },
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    )
                },
                expanded = expanded,
                onExpandedChange = { expanded = it },
            ) {}

            //contents()


            Box(Modifier.weight(1f)) {
                LazyColumn(
                    Modifier
                        .fillMaxSize()
                        .padding(start = 8.dp, end = 8.dp),
                    state = listState,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    loading = photos.loadState.refresh == LoadState.Loading

                    items(photos.itemCount) { index ->
                        val photo = photos[index]
                        if (photo != null) {
                            PhotoItem(
                                photo = photo,
                                onClick = { viewModel.onPhotoClicked(photo) },
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
                    Box(
                        Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PhotoItem(
    photo: PhotoDTO,
    onClick: () -> Unit,
    loading: Boolean
) {
    photo.getThumbnail()?.imageBitmap()?.let {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            )
        ) {

            Image(
                it,
                null,
                Modifier
                    .fillMaxWidth()
                    .clickable(enabled = !loading, onClick = onClick)
                    .padding(8.dp)
                    .clip(shape = RoundedCornerShape(4)),
                contentScale = ContentScale.FillWidth
            )

            Row(
                Modifier.padding(bottom = 8.dp, start = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                if (photo.getBuddyIcon()?.imageBitmap() == null) {
                    Icon(Icons.Filled.AccountCircle, contentDescription = null)
                } else {
                    Image(photo.getBuddyIcon()!!.imageBitmap()!!, null)
                }

                Text(text = photo.owner, textAlign = TextAlign.Left)
            }


        }
    }
}