package com.josemiz.marveldemoapp.navigation

import android.app.Activity
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.josemiz.marveldemoapp.characters.CharacterListView
import com.josemiz.marveldemoapp.comics.ComicsListView
import com.josemiz.marveldemoapp.utils.isTrue
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navigationChannel: Channel<NavigationIntent>, onBottomNavigationClick: (Screen) -> Unit) {
    val navController = rememberNavController()
    val bottomNavigationItems = listOf(
        Screen.ComicList,
        Screen.CharacterList
    )
    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController, bottomNavigationItems, onBottomNavigationClick)
        }
    ) { padding ->
        MainNavigationScreen(modifier = Modifier.padding(padding), navController = navController, navigationChannel = navigationChannel)
    }
}

@Composable
fun MainNavigationScreen(modifier: Modifier, navController: NavHostController, navigationChannel: Channel<NavigationIntent>) {

    val activity = (LocalContext.current as? Activity)
    LaunchedEffect(activity, navController, navigationChannel) {
        navigationChannel.receiveAsFlow().collect { intent ->
            if (activity?.isFinishing == true) {
                return@collect
            }
            when (intent) {
                is NavigationIntent.NavigateBack -> {
                    if (intent.route != null) {
                        navController.popBackStack(intent.route, intent.inclusive)
                    } else {
                        navController.popBackStack()
                    }
                }
                is NavigationIntent.NavigateTo -> {
                    navController.navigate(intent.route) {
                        launchSingleTop = intent.isSingleTop
                        intent.popUpToRoute?.let { popUpToRoute ->
                            popUpTo(popUpToRoute) { inclusive = intent.inclusive }
                        }
                    }
                }
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = Screen.ComicList.route,
        modifier = modifier
    ) {
        composable(Screen.ComicList.route) { ComicsListView(modifier = Modifier, list = listOf()) }
        composable(Screen.CharacterList.route) {
            CharacterListView(
                modifier = Modifier,
                list = listOf()
            )
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController, items: List<Screen>, onBottomNavigationClick: (Screen) -> Unit) {
    BottomNavigation {
        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = backStackEntry?.destination
        items.forEach { screen ->
            BottomNavigationItem(
                icon = {
                    screen.resourceId?.let {
                        Icon(
                            painter = painterResource(id = it),
                            contentDescription = null
                        )
                    }
                },
                label = { Text(text = stringResource(id = screen.stringId)) },
                selected = currentDestination?.hierarchy?.any { it.route == screen.route }.isTrue(),
                onClick = { onBottomNavigationClick.invoke(screen) })
        }
    }
}
