package com.example.photoview.utils

import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap

/**
 * Convert [ByteArray] to an [ImageBitmap]
 * */
fun ByteArray.imageBitmap(): ImageBitmap? {
    return BitmapFactory.decodeByteArray(this, 0, this.size)?.asImageBitmap()
}