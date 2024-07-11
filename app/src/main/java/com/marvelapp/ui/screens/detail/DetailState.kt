package com.marvelapp.ui.screens.detail

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.marvelapp.Result
import com.marvelapp.data.Character

@OptIn(ExperimentalMaterial3Api::class)
class DetailState(
    private val state: Result<Character>,
    val scrollBehavior: TopAppBarScrollBehavior,
    val snackbarHostState: SnackbarHostState
) {
    val character: Character?
        get() = (state as? Result.Success)?.data

    val topBarTitle: String
        get() = character?.name ?: ""
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun rememberDetailState(
    state: Result<Character>,
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }
) = remember(state) { DetailState(state, scrollBehavior, snackbarHostState) }