package com.example.photoview.navigation.routes

import kotlinx.serialization.Serializable


//Extensible way of declaring navigation Routes. For an app this size, one Route suffices.

@Serializable
data object PhotoRoute : Route {

    @Serializable
    data object Search : Page

    @Serializable
    data object ViewPhoto : Page

    @Serializable
    data class ViewUser(val id: String) : Page

    override fun startDestination(): Page = Search
}