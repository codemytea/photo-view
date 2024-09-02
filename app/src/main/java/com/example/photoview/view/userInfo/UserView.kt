package com.example.photoview.view.userInfo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
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
import kotlinx.coroutines.launch

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

    LaunchedEffect(true) {
        scope.launch {
            userInfo = RetrofitClient.flickrApi.userInfo(ownerId)
        }
        viewModel.onSearch("", null, errorDialog::genericError)
    }


    BaseScreen(
        isOnHomePage = false,
        onHome = onHome
    ) { loading, setLoading ->

        Column(
            modifier = Modifier
                .weight(1f)
        ) {

            if (userInfo != null) {
                Card(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
                ) {
                    Column(
                        Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = ownerId,
                            style = MaterialTheme.typography.headlineLarge
                        )
                        Text(text = userInfo!!.profile.profile_description)
                        Text(text = "Joined on " + userInfo!!.profile.join_date.toStringDate())
                    }
                }
            }

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