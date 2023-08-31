package com.josemiz.marveldemoapp.comics.entity

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

data class ComicEntity(
    val title: String,
    val image: String,
    val description: String
)

class ComicProvider : PreviewParameterProvider<ComicEntity> {
    override val values: Sequence<ComicEntity>
        get() = sequenceOf(
            ComicEntity("Iron Man", "", " It´s a cool dude named Tony Stark"),
            ComicEntity("Thor", "", "It´s a cool dude from Asgard"),
            ComicEntity("Capitan America", "", "It´s a cool dude named Steve Rogers")
        )

}