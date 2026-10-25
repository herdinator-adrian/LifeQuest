package com.example.lifequest.navigation

sealed class Routes(val routes: String) {
    object Login: Routes("login")
    object Register: Routes("register")
    object Home: Routes("home")
    object Tasks: Routes("tasks")
    object AddTask: Routes("add_task")
    object Dailies: Routes("dailies")
    object AddDaily: Routes("add_daily")
    object Favorites: Routes("favorites")
    object Profile: Routes("profile")

}