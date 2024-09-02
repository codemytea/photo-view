package com.example.photoview.api

import com.example.photoview.api.DTOs.photoInfo.PhotoInfoDTO
import com.example.photoview.api.DTOs.search.PhotosDTO
import com.example.photoview.api.DTOs.userInfo.UserInfoDTO
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Retrofit Interface for interfacing with the flicker API
 * */
interface FlickrAPI {

    companion object {
        const val PAGE_SIZE: Int = 10 //the number of images per page
    }

    /**
     * @see <a href="https://www.flickr.com/services/api/flickr.photos.search.html">flickr.photos.search</a>
     * */
    @GET("/services/rest/")
    suspend fun search(
        @Query("text") text: String,
        @Query("user_id") userId: String,
        @Query("tags") tags: String,
        @Query("page") page: Int,
        @Query("method") method: String = "flickr.photos.search",
        @Query("safe_search") safeSearch: Int = 1,
        @Query("per_page") perPage: Int = PAGE_SIZE,
        @Query("extras") extras: String = "icon_server,tags",
    ): PhotosDTO

    /**
     * @see <a href="https://www.flickr.com/services/api/flickr.photos.getInfo.html">flickr.photos.getInfo</a>
     * */
    @GET("/services/rest/")
    suspend fun photoInfo(
        @Query("photo_id") photoId: Long,
        @Query("secret") secret: String,
        @Query("method") method: String = "flickr.photos.getInfo",
    ): PhotoInfoDTO

    /**
     * @see <a href="https://www.flickr.com/services/api/flickr.profile.getProfile.html">flickr.profile.getProfile</a>
     * */
    @GET("/services/rest/")
    suspend fun userInfo(
        @Query("user_id") userId: String,
        @Query("method") method: String = "flickr.profile.getProfile",
    ): UserInfoDTO
}
