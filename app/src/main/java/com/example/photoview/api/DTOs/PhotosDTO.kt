package com.example.photoview.api.DTOs

import com.fasterxml.jackson.annotation.JsonAlias
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
class PhotosDTO(
    var photos : SearchDTO = SearchDTO()
)