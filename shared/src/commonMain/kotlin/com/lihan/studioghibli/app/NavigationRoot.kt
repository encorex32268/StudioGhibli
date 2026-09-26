package com.lihan.studioghibli.app

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.lihan.studioghibli.film.presentation.detail.FilmDetailRoot
import com.lihan.studioghibli.film.presentation.film.FilmRoot

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun NavigationRoot(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    SharedTransitionLayout(modifier = modifier) {
        NavHost(
            navController = navController,
            startDestination = Route.FilmRoute
        ) {
            composable<Route.FilmRoute> {
                FilmRoot(
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedVisibilityScope = this@composable,
                    onNavigateToDetail = { filmId ->
                        navController.navigate(Route.FilmDetailRoute(filmId = filmId))
                    }
                )
            }
            composable<Route.FilmDetailRoute> { backStackEntry ->
                val route = backStackEntry.toRoute<Route.FilmDetailRoute>()
                FilmDetailRoot(
                    filmId = route.filmId,
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedVisibilityScope = this@composable,
                    onNavigateBack = {
                        navController.navigateUp()
                    }
                )
            }
        }
    }
}