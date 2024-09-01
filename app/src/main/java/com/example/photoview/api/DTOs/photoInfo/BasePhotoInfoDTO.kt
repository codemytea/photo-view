package com.example.photoview.api.DTOs.photoInfo

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
class BasePhotoInfoDTO(
    var views: String = "",
    var dateuploaded: String = "",
    var title: TitleDTO? = null,
    var description: DescriptionDTO? = null,
)