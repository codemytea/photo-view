package com.example.photoview

import android.graphics.Bitmap
import com.example.photoview.api.DTOs.photoInfo.PhotoInfoDTO
import com.example.photoview.api.DTOs.search.PhotoDTO
import com.example.photoview.api.DTOs.search.PhotosDTO
import com.example.photoview.api.DTOs.userInfo.UserInfoDTO
import com.example.photoview.api.FlickrAPI
import com.example.photoview.api.RetrofitClient
import io.mockk.coEvery
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.runs


fun mockkFlickrApiSearch(photosDTO: PhotosDTO, existingFlickrAPI: FlickrAPI? = null): FlickrAPI {
    if(existingFlickrAPI == null){
        mockkObject(RetrofitClient)
    }

    val flickrApi = existingFlickrAPI ?: mockk<FlickrAPI>()
    coEvery {
        flickrApi.search(any(), any(), any(), any())
    } returns photosDTO

    every { RetrofitClient.flickrApi } returns flickrApi
    return flickrApi
}

fun mockkFlickrApiPhotoInfo(photoInfoDTO: PhotoInfoDTO, existingFlickrAPI: FlickrAPI? = null): FlickrAPI {
    if(existingFlickrAPI == null){
        mockkObject(RetrofitClient)
    }

    val flickrApi = existingFlickrAPI ?: mockk<FlickrAPI>()

    coEvery {
        flickrApi.photoInfo(any(), any())
    } returns photoInfoDTO

    every { RetrofitClient.flickrApi } returns flickrApi
    return flickrApi
}

fun mockkFlickrApiUserInfo(userInfoDTO: UserInfoDTO, existingFlickrAPI: FlickrAPI? = null): FlickrAPI {
    if(existingFlickrAPI == null){
        mockkObject(RetrofitClient)
    }
    val flickrApi = existingFlickrAPI ?: mockk<FlickrAPI>()
    coEvery {
        flickrApi.userInfo(any())
    } returns userInfoDTO
    every { RetrofitClient.flickrApi } returns flickrApi

    return flickrApi
}

fun testBitmap(): Bitmap {
    val arr = IntArray(10000) { it % 256 }
    return Bitmap.createBitmap(arr, 100, 100, Bitmap.Config.ARGB_8888)
}

fun mockPhotoDTO(mOwner: String = "", mTags: String = "", mId: Long = 0L, mSecret: String = ""): PhotoDTO {
    return mockk<PhotoDTO> {
        every { owner } returns mOwner
        every { tags } returns mTags
        every { id } returns mId
        every {secret} returns mSecret
        every { getBuddyIcon() } returns byteArrayOf()
        coEvery { prefetchImageBytes() } just runs
        every { getThumbnail() } returns byteArrayOf()
    }
}
