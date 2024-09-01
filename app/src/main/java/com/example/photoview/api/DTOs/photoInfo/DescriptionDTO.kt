package com.example.photoview.api.DTOs.photoInfo

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
class DescriptionDTO(
    var _content: String = ""
)