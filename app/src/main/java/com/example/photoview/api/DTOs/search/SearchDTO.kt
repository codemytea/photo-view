package com.example.photoview.api.DTOs.search

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

/**
 * @see <a href="https://www.flickr.com/services/api/flickr.photos.search.html">flickr.photos.search</a>
 * */
@JsonIgnoreProperties(ignoreUnknown = true)
class SearchDTO(
    var photo: List<PhotoDTO> = listOf(),
    var page: Int = 0,
    var pages: Int = 0,
    var perpage: Int = 0,
    var total: Int = 0
)