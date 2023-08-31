package com.josemiz.marveldemoapp.characters

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.josemiz.marveldemoapp.R
import com.josemiz.marveldemoapp.characters.entity.CharacterEntity
import com.josemiz.marveldemoapp.characters.entity.CharacterProvider
import com.josemiz.marveldemoapp.ui.theme.MarvelDemoAppTheme

@Composable
fun CharacterListView(modifier: Modifier, list: List<CharacterEntity>) {
    LazyVerticalGrid(modifier = modifier, columns = GridCells.Fixed(2)) {
        if (list.isEmpty()) {
            item(span = { GridItemSpan(2) }) {
                Text(
                    text = "Empty Character",
                    modifier = Modifier.fillMaxSize(),
                    textAlign = TextAlign.Center
                )
            }
        } else {
            items(list) {
                CharacterItem(modifier = Modifier.fillMaxWidth(), character = it)
            }
        }
    }
}

@Composable
fun CharacterItem(modifier: Modifier, character: CharacterEntity) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            modifier = Modifier
                .clip(CircleShape)
                .fillMaxWidth()
                .defaultMinSize(minHeight = 1.dp),
            model = character.url,
            contentScale = ContentScale.Fit,
            placeholder = painterResource(id = R.drawable.deadpool),
            fallback = painterResource(id = R.drawable.deadpool),
            contentDescription = character.name
        )
        Text(
            text = character.name,
            color = Color.White,
            modifier = Modifier.padding(top = 10.dp),
            style = MaterialTheme.typography.labelMedium
        )
    }
}

@Preview
@Composable
fun CharacterListPreview(
    @PreviewParameter(CharacterProvider::class) character: CharacterEntity
) {
    MarvelDemoAppTheme {
        CharacterListView(modifier = Modifier.fillMaxSize().padding(10.dp), listOf(character, character))
    }
}