package com.example.photoview.navigation.routes

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import kotlinx.serialization.Serializable

/**
 * A route (list of pages) which can be navigated to
 */
@Serializable
sealed interface Route : Page {

    // Start screen of the route
    fun startDestination(): Page

    /**
     * Create a [NavHost] associated with this route
     * @param navController Controlling [NavHostController]
     * @param builder Usual input to [NavHost] builder parameter
     * @see [NavHost]
     */
    @Composable
    fun GraphNavHost(
        navController: NavHostController,
        builder: NavGraphBuilder.() -> Unit,
    ) = NavHost(
        navController = navController,
        startDestination = startDestination(),
        builder = builder,
        modifier = Modifier.fillMaxSize(),
    )
}