package com.example.photoview.api.DTOs.search

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
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
    var isfamily: Int = 0,
    var farm: String = "",
    var iconserver: String = "",
    var iconfarm: String = "",
    var tags: String = "",
    var description: String = "",
    var date_upload: String = "",
    var date_taken: String = "",
    var owner_name: String = "",
    var geo: String = "",
    var views: String = "",
) {
    private var thumbnailBytes: ByteArray? = null
    private var buddyIconBytes: ByteArray? = null

    suspend fun prefetchImageBytes() = withContext(Dispatchers.IO) {
        thumbnailBytes = thumbnailUrl().readBytes()
        buddyIconBytes = buddyIcon()?.readBytes()
    }

    fun getThumbnail(): ByteArray? = thumbnailBytes
    fun getBuddyIcon(): ByteArray? = buddyIconBytes

    private fun thumbnailUrl(): URL {
        return URL("https://live.staticflickr.com/$server/${id}_${secret}_z.jpg")
    }

    private fun buddyIcon(): URL? {
        if (iconfarm == "0" || iconserver == "0") return null
        println("http://farm$iconfarm.staticflickr.com/$iconserver/buddyicons/$owner.jpg")
        return URL("http://farm$iconfarm.staticflickr.com/$iconserver/buddyicons/$owner.jpg")
    }
}