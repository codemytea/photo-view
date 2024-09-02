package com.example.photoview.api

import com.example.photoview.api.DTOs.photoInfo.PhotoInfoDTO
import com.example.photoview.api.DTOs.search.PhotosDTO
import com.example.photoview.api.DTOs.userInfo.UserInfoDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickrAPI {

    companion object {
        val pageSize: Int = 10
    }

    @GET("/services/rest/")
    suspend fun search(
        @Query("text") text: String,
        @Query("user_id") userId: String,
        @Query("tags") tags: String,
        @Query("page") page: Int,
        @Query("method") method: String = "flickr.photos.search",
        @Query("safe_search") safeSearch: Int = 1,
        @Query("per_page") perPage: Int = pageSize,
        @Query("extras") extras: String = "icon_server,tags",
    ): PhotosDTO

    @GET("/services/rest/")
    suspend fun photoInfo(
        @Query("photo_id") photoId: Long,
        @Query("secret") secret: String,
        @Query("method") method: String = "flickr.photos.getInfo",
    ): PhotoInfoDTO

    @GET("/services/rest/")
    suspend fun userInfo(
        @Query("user_id") userId: String,
        @Query("method") method: String = "flickr.profile.getProfile",
    ): UserInfoDTO
}
