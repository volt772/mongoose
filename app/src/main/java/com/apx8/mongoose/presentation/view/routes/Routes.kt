package com.apx8.mongoose.presentation.view.routes

sealed class Routes(val route: String) {
    data object Stadium: Routes("StadiumListScreen")
}