package com.example.photoview.navigation.graphs

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.photoview.navigation.animation.slideInLeftComposable
import com.example.photoview.navigation.popBackStackOr
import com.example.photoview.navigation.routes.PhotoViewRoute
import com.example.photoview.view.HomeScreen

@Composable
fun PhotoViewGraph() {
    val navController = rememberNavController()

    val onBack: () -> Unit = { navController.popBackStackOr {} }

    PhotoViewRoute.GraphNavHost(navController = navController) {
        slideInLeftComposable<PhotoViewRoute.Home> {
            val onNext = {}
            HomeScreen(
                onNext,
            )
        }

    }
}