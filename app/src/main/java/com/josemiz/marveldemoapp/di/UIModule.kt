package com.josemiz.marveldemoapp.di

import com.josemiz.marveldemoapp.navigation.NavigationManager
import com.josemiz.marveldemoapp.navigation.NavigationManagerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UIModule {

    @Binds
    abstract fun bindNavigationManager(
        navigationManagerImpl: NavigationManagerImpl
    ) : NavigationManager

}