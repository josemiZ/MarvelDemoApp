package com.josemiz.marveldemoapp.navigation

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val navigationManager: NavigationManager
) : ViewModel() {
    val navigationChannel = navigationManager.navigationChannel

    fun navigateToScreenFromBottomBar(screen: Screen) {
        navigationManager.tryNavigateTo(screen, inclusive = true, isSingleTop = true)
    }
}