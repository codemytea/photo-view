package com.example.photoview.navigation.animation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute

const val animationTime = 500

/**
 * Composable which enters slide in from the left
 * @param content The composable to display on arrival at this route
 */
inline fun <reified T : Any> NavGraphBuilder.slideInLeftComposable(
    noinline content: @Composable AnimatedContentScope.(T) -> Unit,
) = composable<T>(
    content = {
        val args = it.toRoute<T>()
        content(args)
    },
    enterTransition = {
        fadeIn(tween(animationTime)) +
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(animationTime),
                    initialOffset = { it / 2 },
                )
    },
    exitTransition = {
        fadeOut(tween(animationTime)) +
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(animationTime),
                    targetOffset = { it / 2 },
                )
    },
    popEnterTransition = {
        fadeIn(tween(animationTime)) +
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(animationTime),
                    initialOffset = { it / 2 },
                )
    },
    popExitTransition = {
        fadeOut(tween(animationTime)) +
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(animationTime),
                    targetOffset = { it / 2 },
                )
    },
)
