package com.example.photoview.api.DTOs.search

import com.fasterxml.jackson.annotation.JsonIgnoreProperties


@JsonIgnoreProperties(ignoreUnknown = true)
class SearchDTO(
    var photo: List<PhotoDTO> = listOf(),
    var page: Int = 0,
    var pages: Int = 0,
    var perpage: Int = 0,
    var total: Int = 0
)