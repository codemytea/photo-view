package com.example.photoview.api.DTOs

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import java.net.URL

@JsonIgnoreProperties(ignoreUnknown = true)
class PhotoDTO(
    var id: Long = 0,
    var owner: String = "",
    var secret: String = "",
    var server: Int = 0,
    var title: String = "",
    var ispublic: Int = 0,
    var isfriend: Int = 0,
    var isfamily: Int = 0
){
    private lateinit var thumbnailBytes: ByteArray
    private var originalBytes: ByteArray? = null

    suspend fun prefetchImageBytes() = withContext(Dispatchers.IO){
        thumbnailBytes = thumbnailUrl().readBytes()
    }

    fun getThumbnail(): ByteArray = thumbnailBytes

    suspend fun getOriginal() = withContext(Dispatchers.IO){
        originalBytes ?: let{
            originalUrl().readBytes().also{
                thumbnailBytes = it
            }
        }
    }

    private fun thumbnailUrl(): URL{
        return URL("https://live.staticflickr.com/$server/${id}_${secret}_z.jpg")
    }
    private fun originalUrl(): URL{
        return URL("https://www.flickr.com/photos/$owner/$id")
    }
}