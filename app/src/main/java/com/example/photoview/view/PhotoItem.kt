package com.example.photoview.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.photoview.api.DTOs.search.PhotoDTO
import com.example.photoview.utils.imageBitmap

@Composable
fun PhotoItem(
    photo: PhotoDTO,
    onClick: () -> Unit,
    onUser: () -> Unit,
    loading: Boolean,
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
                Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Button(onClick = { onUser() }, Modifier.fillMaxWidth()) {
                    if (photo.getBuddyIcon()?.imageBitmap() == null) {
                        Icon(Icons.Filled.AccountCircle, contentDescription = null)
                    } else {
                        Image(photo.getBuddyIcon()!!.imageBitmap()!!, null)
                    }

                    Text(text = " " + photo.owner, textAlign = TextAlign.Left)
                }
            }

            if (photo.tags.isNotEmpty()) {
                Text(
                    text = "Tags: " + photo.tags.trim().replace(" ", ", ").lowercase(),
                    fontSize = 12.sp,
                    modifier = Modifier.padding(8.dp)
                )
            }


        }
    }
}