package com.marvelapp.ui.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.marvelapp.data.Character
import com.marvelapp.ui.screens.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onClick: (Character) -> Unit,
    fetchNextPage: () -> Unit,
    state: HomeViewModel.UiState
) {
    Screen {
        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "Characters") },
                    scrollBehavior = scrollBehavior
                )
            },
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            contentWindowInsets = WindowInsets.safeDrawing
        ) { padding ->
            StartUi(state, padding, fetchNextPage, onClick)
        }
    }
}

@Composable
private fun StartUi(
    state: HomeViewModel.UiState,
    padding: PaddingValues,
    fetchNextPage: () -> Unit,
    onClick: (Character) -> Unit
) {
    if (state.loading && state.characters.isEmpty()) {
        LoadingBar(padding)
    } else {
        LoadApi(padding, state, fetchNextPage, onClick)
    }
}

@Composable
private fun LoadApi(
    padding: PaddingValues,
    state: HomeViewModel.UiState,
    fetchNextPage: () -> Unit,
    onClick: (Character) -> Unit
) {
    LaunchedEffect(Unit) {
        fetchNextPage()
    }
    LazyVerticalGrid(
        columns = GridCells.Adaptive(120.dp),
        contentPadding = padding,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier.padding(horizontal = 4.dp)
    ) {
        itemsIndexed(state.characters) { index, character ->
            if (index == state.characters.size - 1) {
                LaunchedEffect(Unit) {
                    fetchNextPage()
                }
            }
            CharacterItem(
                character = character,
                onClick = { onClick(character) },
                index = index
            )
        }
        item {}
        item {
            val h = padding.calculateStartPadding(LayoutDirection.Ltr)
            LoadingBar(PaddingValues(h, 8.dp))
        }
    }
}

@Composable
private fun LoadingBar(
    padding: PaddingValues
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Preview
@Composable
fun LoadingBarPreview() {
    MaterialTheme {
        LoadingBar(padding = PaddingValues(16.dp))
    }
}

@Composable
private fun CharacterItem(character: Character, onClick: (Character) -> Unit, index: Int) {
    Column(
        modifier = Modifier.clickable { onClick(character) }
    ) {
        val imageUrl = character.thumbnail?.let { "${it.path}.${it.extension}" }
        AsyncImage(
            model = imageUrl,
            contentDescription = character.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(2 / 3f)
                .clip(MaterialTheme.shapes.small)
        )
        Text(
            text = character.name!!,
            style = MaterialTheme.typography.bodySmall,
            maxLines = 1,
            modifier = Modifier.padding(8.dp)
        )
    }
}



