package com.example.photoview.api.DTOs.search

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
class PhotosDTO(
    var photos : SearchDTO = SearchDTO()
)