package com.example.photoview.api

import com.example.photoview.api.DTOs.PhotosDTO
import com.example.photoview.api.DTOs.SearchDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickrAPI {

    companion object{
        val pageSize: Int = 20
    }

    @GET("/services/rest/")
    suspend fun search(
        @Query("text") text: String, //"page" = what page to load, "sort" = for custom sorting, "text" = what to search
        @Query("page") page: Int,
        @Query("method") method: String = "flickr.photos.search",
        @Query("safe_search") safeSearch: Int = 1,
        @Query("per_page") perPage : Int = 20
    ) : PhotosDTO
}