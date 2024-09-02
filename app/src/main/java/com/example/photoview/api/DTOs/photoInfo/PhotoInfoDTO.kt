package com.example.photoview.api.DTOs.photoInfo

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

/**
 * @see <a href="https://www.flickr.com/services/api/flickr.photos.getInfo.html">flickr.photos.getInfo</a>
 * */
@JsonIgnoreProperties(ignoreUnknown = true)
class PhotoInfoDTO (
    var photo : BasePhotoInfoDTO? = null,
)