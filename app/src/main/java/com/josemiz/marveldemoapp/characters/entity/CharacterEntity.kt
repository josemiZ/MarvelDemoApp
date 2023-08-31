package com.josemiz.marveldemoapp.characters.entity

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

data class CharacterEntity(
    val name: String,
    val description: String,
    val url: String
)

// Make this a separate file and put in gitignore just for good measure
class CharacterProvider : PreviewParameterProvider<CharacterEntity> {
    override val values: Sequence<CharacterEntity>
        get() = sequenceOf(
            CharacterEntity("Iron Man", " It´s a cool dude named Tony Stark", ""),
            CharacterEntity("Thor", "It´s a cool dude from Asgard", ""),
            CharacterEntity("Capitan America", "It´s a cool dude named Steve Rogers", "")
        )

}
