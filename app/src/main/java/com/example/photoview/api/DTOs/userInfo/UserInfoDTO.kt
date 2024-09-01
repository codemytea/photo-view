package com.example.photoview.api.DTOs.userInfo

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
class UserInfoDTO (
    var profile : BaseUserInfoDTO = BaseUserInfoDTO()
)