package com.example.photoview.navigation.routes

import kotlinx.serialization.Serializable

@Serializable
data object PhotoViewRoute : Route{

    @Serializable
    data object Home : Page

    override fun startDestination(): Page = Home
}