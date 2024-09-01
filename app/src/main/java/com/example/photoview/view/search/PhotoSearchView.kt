package com.example.photoview.view.search

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.photoview.api.DTOs.search.PhotoDTO
import com.example.photoview.view.BaseScreen
import com.example.photoview.view.PagingPhotoView
import com.example.photoview.view.PagingViewModel
import com.example.photoview.view.rememberDialog
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoSearchView(
    onPhoto: (photo: PhotoDTO) -> Unit,
    onUser: (photo: PhotoDTO) -> Unit,
) {
    val viewModel: PagingViewModel = viewModel()
    var text by rememberSaveable { mutableStateOf("Yorkshire") }
    var expanded by rememberSaveable { mutableStateOf(false) }
    val errorDialog = rememberDialog()
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(true) {
        viewModel.onSearch("Yorkshire", "", errorDialog::genericError)
    }


    BaseScreen(
        onHome = {}
    ) { loading, setLoading ->

        DockedSearchBar(
            modifier = Modifier
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
                        viewModel.onSearch(it, "", errorDialog::genericError)
                    },
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
        ) {}


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
