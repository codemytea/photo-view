package com.example.photoview.view.search

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.photoview.api.DTOs.search.PhotoDTO
import com.example.photoview.view.BaseScreen
import com.example.photoview.view.PagingPhotoView
import com.example.photoview.view.PagingViewModel
import com.example.photoview.view.common.rememberDialog
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoSearchView(
    onPhoto: (photo: PhotoDTO) -> Unit,
    onUser: (photo: PhotoDTO) -> Unit,
) {
    BackHandler {} //so clicking back on home page doesn't exit the app

    val viewModel: PagingViewModel = viewModel()
    var text by rememberSaveable { mutableStateOf("Yorkshire") }
    val errorDialog = rememberDialog()
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    var expanded by rememberSaveable { mutableStateOf(false) }

    val onSearch: (String, SearchType?) -> Unit = { query, searchType ->
        scope.launch {
            listState.scrollToItem(0)
        }
        expanded = false
        viewModel.onSearch(query, searchType, errorDialog::genericError)
    }

    LaunchedEffect(true) {
        viewModel.onSearch(text, null, errorDialog::genericError)
    }

    BaseScreen(
        onHome = {}
    ) { loading, setLoading ->

        DockedSearchBar(
            modifier = Modifier.wrapContentHeight(),
            inputField = {
                SearchBarDefaults.InputField(
                    enabled = !loading,
                    query = text,
                    onQueryChange = { text = it },
                    onSearch = { onSearch(it, null) },
                    expanded = expanded,
                    onExpandedChange = { expanded = it },
                    placeholder = { Text("Search") },
                    leadingIcon = {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = "Search Icon"
                        )
                    },
                )
            },
            expanded = expanded,
            onExpandedChange = { expanded = it },
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            repeat(3) { index ->
                ListItem(
                    headlineContent = { Text(SearchType.entries[index].text + text) },
                    modifier = Modifier
                        .clickable {
                            onSearch(text, SearchType.entries[index])
                            expanded = false
                        }
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(horizontal = 16.dp, vertical = 4.dp),
                )
            }
        }

        PagingPhotoView(
            setLoading,
            loading,
            listState,
            onPhoto,
            onUser,
            viewModel
        )
    }
}