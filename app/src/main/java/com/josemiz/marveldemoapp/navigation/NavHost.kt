package com.josemiz.marveldemoapp.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.navigation.NamedNavArgument
import com.josemiz.marveldemoapp.R

sealed class Screen(val route: String, @StringRes val stringId: Int, @DrawableRes val resourceId: Int? = null) {
    object CharacterList : Screen("character", R.string.characters, R.drawable.baseline_person_outline_24)
    object ComicList : Screen("comic", R.string.comics, R.drawable.baseline_menu_book_24)
    object CharacterDetail : Screen("character/{id}", R.string.characters)
    object ComicDetail : Screen("comic/{id}", R.string.comics)
}