package com.example.photoview.api.DTOs.photoInfo

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
class TitleDTO(
    var _content: String = ""
)