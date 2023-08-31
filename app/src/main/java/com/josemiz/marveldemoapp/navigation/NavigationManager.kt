package com.josemiz.marveldemoapp.navigation

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import javax.inject.Inject

interface NavigationManager {
    val navigationChannel: Channel<NavigationIntent>

    suspend fun navigateBack(
        route: Screen? = null,
        inclusive: Boolean = false,
    )

    fun tryNavigateBack(
        route: Screen? = null,
        inclusive: Boolean = false,
    )

    suspend fun navigateTo(
        route: Screen,
        popUpToRoute: Screen? = null,
        inclusive: Boolean = false,
        isSingleTop: Boolean = false,
    )

    fun tryNavigateTo(
        route: Screen,
        popUpToRoute: Screen? = null,
        inclusive: Boolean = false,
        isSingleTop: Boolean = false,
    )
}

class NavigationManagerImpl @Inject constructor() : NavigationManager {

    override val navigationChannel = Channel<NavigationIntent>(
        capacity = Int.MAX_VALUE,
        onBufferOverflow = BufferOverflow.DROP_LATEST,
    )

    override suspend fun navigateBack(route: Screen?, inclusive: Boolean) {
        navigationChannel.send(
            NavigationIntent.NavigateBack(
                route = route?.route,
                inclusive = inclusive
            )
        )
    }

    override fun tryNavigateBack(route: Screen?, inclusive: Boolean) {
        navigationChannel.trySend(
            NavigationIntent.NavigateBack(
                route = route?.route,
                inclusive = inclusive
            )
        )
    }

    override suspend fun navigateTo(
        route: Screen,
        popUpToRoute: Screen?,
        inclusive: Boolean,
        isSingleTop: Boolean
    ) {
        navigationChannel.send(
            NavigationIntent.NavigateTo(
                route = route.route,
                popUpToRoute = popUpToRoute?.route,
                inclusive = inclusive,
                isSingleTop = isSingleTop,
            )
        )
    }

    override fun tryNavigateTo(
        route: Screen,
        popUpToRoute: Screen?,
        inclusive: Boolean,
        isSingleTop: Boolean
    ) {
        navigationChannel.trySend(
            NavigationIntent.NavigateTo(
                route = route.route,
                popUpToRoute = popUpToRoute?.route,
                inclusive = inclusive,
                isSingleTop = isSingleTop,
            )
        )
    }

}

sealed class NavigationIntent {
    data class NavigateBack(
        val route: String? = null,
        val inclusive: Boolean = false,
    ) : NavigationIntent()

    data class NavigateTo(
        val route: String,
        val popUpToRoute: String? = null,
        val inclusive: Boolean = false,
        val isSingleTop: Boolean = false,
    ) : NavigationIntent()
}