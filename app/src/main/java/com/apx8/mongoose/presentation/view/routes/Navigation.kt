package com.apx8.mongoose.view.routes

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.apx8.mongoose.view.screen.StadiumListScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.Stadium.route
    ) {

        composable(Routes.Stadium.route) {
            StadiumListScreen(navController = navController)
        }
    }
}
