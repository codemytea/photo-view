package com.example.photoview.view.userInfo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.photoview.api.DTOs.search.PhotoDTO
import com.example.photoview.api.DTOs.userInfo.UserInfoDTO
import com.example.photoview.api.RetrofitClient
import com.example.photoview.utils.toStringDate
import com.example.photoview.view.BaseScreen
import com.example.photoview.view.PagingPhotoView
import com.example.photoview.view.PagingViewModel
import com.example.photoview.view.common.rememberDialog
import com.example.photoview.view.search.SearchType
import kotlinx.coroutines.launch

/**
 * View to display more info about the user.
 *
 * @param onHome Function to navigate to PhotoSearchView,
 * @param onPhoto Function to execute on image click.
 * @param ownerId The user that has been selected.
 * */
@Composable
fun UserView(
    onHome: () -> Unit,
    onPhoto: (PhotoDTO) -> Unit,
    ownerId: String
) {
    val viewModel: PagingViewModel = viewModel()
    var userInfo by remember { mutableStateOf<UserInfoDTO?>(null) }
    val scope = rememberCoroutineScope()
    val errorDialog = rememberDialog()
    var expanded by remember { mutableStateOf(true) }

    //Load more info about the user from the FlickrAPI
    LaunchedEffect(true) {
        scope.launch {
            userInfo = RetrofitClient.flickrApi.userInfo(ownerId)
        }
        viewModel.onSearch(ownerId, SearchType.USER, errorDialog::genericError)
    }


    BaseScreen(
        isOnHomePage = false,
        onHome = onHome
    ) { loading, setLoading ->

        Column(modifier = Modifier.weight(1f)) {

            if (userInfo != null) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                ) {
                    Column(
                        Modifier.padding(16.dp)
                    ) {
                        Row {
                            Text(
                                text = ownerId,
                                style = MaterialTheme.typography.headlineLarge
                            )
                            Spacer(modifier = Modifier.weight(1f))

                            //User Information can take up a lot of space, which will cover the photos
                            //as this part of the screen is static and doesn't scroll.
                            //As such, allow user to collapse information to view images below
                            Button(onClick = { expanded = !expanded }) {
                                if (expanded) Icon(
                                    Icons.Filled.KeyboardArrowUp,
                                    contentDescription = "Collapse"
                                ) else Icon(
                                    Icons.Filled.ArrowDropDown,
                                    contentDescription = "Expand"
                                )
                            }
                        }

                        if (expanded){
                            Text(text = userInfo!!.profile.profile_description)
                            Text(text = "Joined on " + userInfo!!.profile.join_date.toStringDate())
                        }
                        
                    }
                }
            } else {
                Text("User Information unavailable")
            }

            //Images associated with the user
            PagingPhotoView(
                setLoading = setLoading,
                loading = loading,
                listState = rememberLazyListState(),
                onPhoto = onPhoto,
                onUser = {},
                viewModel
            )
        }
    }
}