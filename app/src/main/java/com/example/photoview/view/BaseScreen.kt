package com.example.photoview.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * A generic base screen that all main views use.
 *
 * @param isOnHomePage Whether the user is currently on the home page. Allows for highlighting of the home icon appropriately.
 * @param onHome Function to be executed when NavigationBarIcon is clicked.
 * @param contents Contents to draw inside the screen.
 * */
@Composable
fun BaseScreen(
    isOnHomePage: Boolean = true,
    onHome: () -> Unit,
    contents: @Composable ColumnScope.(Boolean, (Boolean) -> Unit) -> Unit = { _, _ -> },
) {
    val (loading, setLoading) = rememberSaveable { mutableStateOf(false) }

    Column(
        modifier =
        Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(MaterialTheme.colorScheme.background)
            .padding(top = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        contents(loading, setLoading)

        //Ensure safe areas are protected in edgeToEdge mode (SDK >= 35)
        NavigationBar(
        ) {
            NavigationBarItem(
                icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
                label = { Text("Home") },
                selected = isOnHomePage,
                onClick = { onHome() },
                modifier = Modifier.wrapContentHeight()

            )
        }
    }
}