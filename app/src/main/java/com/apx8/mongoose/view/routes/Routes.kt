package com.apx8.mongoose.view.routes

sealed class Routes(val route: String) {
    data object Stadium: Routes("StadiumListScreen")
}