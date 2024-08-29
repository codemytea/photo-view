package com.example.photoview.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun PhotoViewScreen(
    contents: @Composable (Boolean, (Boolean) -> Unit) -> Unit = { _, _ -> },
) {
    val (loading, setLoading) = rememberSaveable { mutableStateOf(false) }
    var text by rememberSaveable { mutableStateOf("") }
    var expanded by rememberSaveable { mutableStateOf(false) }

    Surface {
        Column {

            Box(
                modifier = Modifier
                    .height(56.dp)
                    .fillMaxWidth()
            ) {

                SearchBar(
                    inputField = {
                        SearchBarDefaults.InputField(
                            query = text,
                            onQueryChange = { text = it },
                            onSearch = { expanded = false },
                            expanded = expanded,
                            onExpandedChange = { expanded = it },
                            placeholder = { Text("Search") },
                            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                            trailingIcon = {
                                Icon(
                                    Icons.Default.MoreVert,
                                    contentDescription = null
                                )
                            },
                        )
                    },
                    expanded = expanded,
                    onExpandedChange = { expanded = it },
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(horizontal = 8.dp)
                        .align(Alignment.CenterStart),
                ) {

                }
            }

            //The main part of the page
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .weight(weight = 1f, fill = false),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                contents(loading, setLoading)
            }


        }
    }
}