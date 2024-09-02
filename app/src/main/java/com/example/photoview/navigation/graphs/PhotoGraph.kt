package com.example.photoview.navigation.graphs

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.rememberNavController
import com.example.photoview.api.DTOs.search.PhotoDTO
import com.example.photoview.navigation.animation.slideInLeftComposable
import com.example.photoview.navigation.routes.PhotoRoute
import com.example.photoview.navigation.safeNavigate
import com.example.photoview.view.photoInfo.PhotoView
import com.example.photoview.view.search.PhotoSearchView
import com.example.photoview.view.userInfo.UserView

@Composable
fun PhotoViewGraph() {
    val navController = rememberNavController()
    var photo by remember {
        mutableStateOf<PhotoDTO?>(null)
    }

    val onHome: () -> Unit = { navController.popBackStack(PhotoRoute.Search, false) }
    val onUser: (PhotoDTO) -> Unit = { navController.safeNavigate(PhotoRoute.ViewUser(it.owner)) }
    val onPhoto: (PhotoDTO) -> Unit = {
        photo = it
        navController.safeNavigate(PhotoRoute.ViewPhoto)
    }

    PhotoRoute.GraphNavHost(navController = navController) {
        slideInLeftComposable<PhotoRoute.Search> {
            PhotoSearchView(
                onPhoto,
                onUser,
            )
        }

        slideInLeftComposable<PhotoRoute.ViewPhoto> {
            PhotoView(
                onHome,
                photo
            )
        }

        slideInLeftComposable<PhotoRoute.ViewUser> {
            UserView(
                onHome,
                onPhoto,
                it.id,
            )
        }
    }
}