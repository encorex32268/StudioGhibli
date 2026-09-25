package com.lihan.studioghibli.app

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lihan.studioghibli.film.presentation.film.FilmRoot

@Composable
fun NavigationRoot(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Route.FilmRoute
    ){
        composable<Route.FilmRoute> {
            FilmRoot(
                onNavigateToDetail = {

                }
            )
        }
    }

}