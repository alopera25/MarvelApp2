package com.marvelapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.marvelapp.App
import com.marvelapp.data.CharacterRepository
import com.marvelapp.data.datasource.CharacterLocalDataSource
import com.marvelapp.data.datasource.CharacterRemoteDataSource
import com.marvelapp.ui.screens.detail.DetailScreen
import com.marvelapp.ui.screens.detail.DetailViewModel
import com.marvelapp.ui.screens.home.HomeScreen
import com.marvelapp.ui.screens.home.HomeViewModel
import com.marvelapp.ui.screens.splash.SplashScreen
import com.marvelapp.ui.screens.splash.SplashViewModel
import com.marvelapp.usecases.FetchCharactersUseCase
import com.marvelapp.usecases.FindCharacterByIdUseCase
import com.marvelapp.usecases.ToggleFavoriteUseCase

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val app = LocalContext.current.applicationContext as App

    val characterRepository= CharacterRepository(
        CharacterRemoteDataSource(),
        CharacterLocalDataSource(app.db.characterDao()),
    )

    NavHost(navController = navController, startDestination = NavScreen.Splash.route) {

        composable(NavScreen.Splash.route) {
            SplashScreen(navController, SplashViewModel()) {
            }
        }

        composable(NavScreen.Home.route) {
            HomeScreen(
                viewModel { HomeViewModel(FetchCharactersUseCase(characterRepository)) },
                onClick = { character ->
                    navController.navigate(NavScreen.Detail.createRoute(character.id!!))
                }
            )
        }

        composable(
            route = NavScreen.Detail.route,
            arguments = listOf(navArgument(NavArgs.CharacterId.key) { type = NavType.IntType })
        ) { backStackEntry ->
            val characterId =
                requireNotNull(backStackEntry.arguments?.getInt(NavArgs.CharacterId.key))
            DetailScreen(
                viewModel {
                    DetailViewModel(
                        characterId,
                        FindCharacterByIdUseCase(characterRepository),
                        ToggleFavoriteUseCase(characterRepository)
                    )
                },
                onBack = { navController.popBackStack() })
        }
    }
}