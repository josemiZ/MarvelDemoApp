package com.josemiz.marveldemoapp.comics

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import com.josemiz.marveldemoapp.R
import com.josemiz.marveldemoapp.comics.entity.ComicEntity
import com.josemiz.marveldemoapp.comics.entity.ComicProvider
import com.josemiz.marveldemoapp.ui.theme.MarvelDemoAppTheme

@Composable
fun ComicsListView(modifier: Modifier, list: List<ComicEntity>) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        if (list.isEmpty()) {
            item {
                Text(
                    text = "Empty Comic",
                    modifier = Modifier.fillMaxSize(),
                    textAlign = TextAlign.Center
                )
            }
        } else {
            items(list) {
                ComicItem(modifier = Modifier.fillMaxSize(), comic = it)
            }
        }
    }
}

@Composable
fun ComicItem(modifier: Modifier, comic: ComicEntity) {
    ConstraintLayout(modifier = modifier) {
        val (image, title, description) = createRefs()

        AsyncImage(
            modifier = Modifier.constrainAs(image) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                width = Dimension.value(80.dp)
                height = Dimension.fillToConstraints
            },
            model = comic.image,
            contentScale = ContentScale.Fit,
            placeholder = painterResource(id = R.drawable.deadpool),
            fallback = painterResource(id = R.drawable.deadpool),
            contentDescription = comic.title
        )
        Text(comic.title, modifier = Modifier.constrainAs(title) {
            top.linkTo(parent.top)
            start.linkTo(image.end)
            end.linkTo(parent.end)
            width = Dimension.fillToConstraints
        })
        Text(comic.description, modifier = Modifier.constrainAs(description) {
            top.linkTo(title.bottom)
            start.linkTo(image.end)
            end.linkTo(parent.end)
            bottom.linkTo(parent.bottom)
            width = Dimension.fillToConstraints
        })

    }
}

@Preview
@Composable
fun ComicListPreview(@PreviewParameter(ComicProvider::class) comic: ComicEntity) {
    MarvelDemoAppTheme {
        ComicsListView(modifier = Modifier.fillMaxSize(), listOf(comic, comic, comic))
    }
}

