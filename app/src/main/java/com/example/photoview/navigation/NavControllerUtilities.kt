package com.example.photoview.navigation


import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.toRoute
import com.example.photoview.navigation.routes.Page
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

/**
 * Pop back stack, or perform additional function if no screens exist in the backstack
 * @param ifEmpty Function to execute if pop back would otherwise result in a blank screen
 */
fun NavController.popBackStackOr(ifEmpty: () -> Unit) =
    CoroutineScope(Dispatchers.Main).launch {
        delay(100) // Delay makes navigation smoother
        if (!this@popBackStackOr.popBackStack()) ifEmpty()
    }.let { }

/**
 * Navigate to a [Page]
 * @param page The [Page] to navigate to
 */
fun NavController.safeNavigate(page: @Serializable Page) =
    CoroutineScope(Dispatchers.Main).launch {
        delay(100) // Delay makes navigation smoother
        if (this@safeNavigate.currentDestination?.hasRoute(page::class) == false) {
            navigate(page)
        }
    }.let { }

fun Map<String, String>.toLoggerString(): String {
    val outArray = mutableListOf<String>()
    for ((key, value) in this) {
        outArray.add("$key='$value'")
    }
    return outArray.joinToString("&")
}

inline fun <reified T : Page> NavController.getBackStackEntryOrNull(): T? {
    return try {
        getBackStackEntry<T>().toRoute<T>()
    } catch (e: IllegalArgumentException) {
        null
    }
}

