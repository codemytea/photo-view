package com.example.photoview.api.DTOs.userInfo

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

/**
 * @see <a href="https://www.flickr.com/services/api/flickr.profile.getProfile.html">flickr.profile.getProfile</a>
 * */
@JsonIgnoreProperties(ignoreUnknown = true)
class BaseUserInfoDTO (
    var join_date : Long = 0,
    var profile_description : String = "",
)