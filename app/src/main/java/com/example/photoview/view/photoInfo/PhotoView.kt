package com.example.photoview.view.photoInfo

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.example.photoview.api.DTOs.photoInfo.PhotoInfoDTO
import com.example.photoview.api.DTOs.search.PhotoDTO
import com.example.photoview.api.RetrofitClient
import com.example.photoview.utils.imageBitmap
import com.example.photoview.utils.toStringDate
import com.example.photoview.view.BaseScreen
import com.example.photoview.view.LoadingView
import kotlinx.coroutines.launch

@Composable
fun PhotoView(
    onHome: () -> Unit,
    photo: PhotoDTO?
) {
    var photoInfo by remember {
        mutableStateOf<PhotoInfoDTO?>(null)
    }

    val scope = rememberCoroutineScope()


    BaseScreen(
        isOnHomePage = false,
        onHome = onHome
    ) { loading, setLoading ->

        LaunchedEffect(true) {
            setLoading(true)
            if (photo?.id != null) {
                scope.launch {
                    photoInfo = RetrofitClient.flickrApi.photoInfo(photo.id, photo.secret)
                }
            }
            setLoading(false)
        }

        if (loading) {
            LoadingView()
        }


        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {

            if (photoInfo != null) {
                Text(
                    photoInfo!!.photo?.title?._content ?: "",
                    style = MaterialTheme.typography.headlineLarge,
                )
                Text(
                    photoInfo!!.photo?.description?._content ?: "",
                )
            }

            photo?.getThumbnail()?.imageBitmap()?.let {
                Image(
                    it,
                    null,
                    Modifier
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(4)),
                    contentScale = ContentScale.FillWidth
                )
            }


            if (photoInfo != null) {
                photoInfo!!.photo?.views?.let {
                    Text("Views: $it")
                }
                photoInfo!!.photo?.dateuploaded?.let {
                    Text("Date Uploaded: " + it.toStringDate())
                }

            }
        }
    }
}