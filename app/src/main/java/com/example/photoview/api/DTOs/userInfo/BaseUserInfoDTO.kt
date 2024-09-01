package com.example.photoview.api.DTOs.userInfo

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
class BaseUserInfoDTO (
    var join_date : String = "",
    var profile_description : String = "",
)