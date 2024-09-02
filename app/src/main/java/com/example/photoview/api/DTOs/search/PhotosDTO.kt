package com.example.photoview.api.DTOs.search

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

/**
 * @see <a href="https://www.flickr.com/services/api/flickr.photos.search.html">flickr.photos.search</a>
 * */
@JsonIgnoreProperties(ignoreUnknown = true)
class PhotosDTO(
    var photos: SearchDTO = SearchDTO()
)