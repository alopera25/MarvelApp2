package com.marvelapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.marvelapp.App
import com.marvelapp.data.CharacterRepository
import com.marvelapp.data.datasource.CharacterLocalDataSource
import com.marvelapp.data.datasource.CharacterRemoteDataSource
import com.marvelapp.ui.screens.detail.DetailScreen
import com.marvelapp.ui.screens.detail.DetailViewModel
import com.marvelapp.ui.screens.home.HomeScreen
import com.marvelapp.ui.screens.splash.SplashScreen
import com.marvelapp.ui.screens.splash.SplashViewModel

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val app = LocalContext.current.applicationContext as App

    val characterRepository = remember {
        CharacterRepository(
            CharacterRemoteDataSource(),
            CharacterLocalDataSource(app.db.characterDao()),
        )
    }

    NavHost(navController = navController, startDestination = Splash) {
        composable<Splash> {
            SplashScreen(navController,SplashViewModel()) {
            }
        }

        composable<Home> {
            HomeScreen(onClick = { character ->
                navController.navigate(Detail(character.id!!))
            })
        }

        composable<Detail> { backStackEntry ->
            val detail = backStackEntry.toRoute<Detail>()
            DetailScreen(
                viewModel { DetailViewModel(detail.characterId) },
                onBack = { navController.popBackStack() })
        }
    }
}