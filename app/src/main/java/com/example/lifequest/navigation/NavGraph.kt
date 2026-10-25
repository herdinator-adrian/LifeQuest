package com.example.lifequest.navigation

import com.example.lifequest.View.CartView
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.lifequest.Components.AddDailyMenu
import com.example.lifequest.Data.database.AppDatabase
import com.example.lifequest.Data.repository.TaskRepository
import com.example.lifequest.Components.AddTaskMenu
import com.example.lifequest.View.DailiesView
import com.example.lifequest.View.FavoritesView
import com.example.lifequest.View.HomeView
import com.example.lifequest.View.LoginView
import com.example.lifequest.View.ProfileView
import com.example.lifequest.View.RegisterView
import com.example.lifequest.View.TasksView
import com.google.firebase.auth.FirebaseAuth

@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val context = LocalContext.current
    val database = AppDatabase.getDatabase(context)
    // Verificar si hay sesión activa
    val startRoute = if (FirebaseAuth.getInstance().currentUser != null) {
        Routes.Home.routes
    } else {
        Routes.Login.routes
    }

    NavHost(
        navController = navController,
        startDestination = startRoute,
        modifier = modifier
    ) {
        composable(Routes.Login.routes) {
            LoginView(
                onNavigateToRegister = { navController.navigate(Routes.Register.routes) },
                onLoginSuccess = {
                    navController.navigate(Routes.Home.routes) {
                        popUpTo(Routes.Login.routes) { inclusive = true }
                    }
                },
                modifier = modifier
            )
        }
        composable(Routes.Register.routes) {
            RegisterView(
                onNavigateToLogin = { navController.navigate(Routes.Login.routes) },
                onRegisterSuccess = {
                    navController.navigate(Routes.Home.routes) {
                        popUpTo(Routes.Register.routes) { inclusive = true }
                    }
                },
            )
        }

        composable(Routes.Home.routes) {
            HomeView(
                onItemSelected = { index ->
                    navigateByBar(navController, index)
                },
                onCartClick = {
                    //navController.navigate(Routes.Cart.routes)
                }
            )
        }
        composable(Routes.Tasks.routes) {
            TasksView(
                onItemSelected = { index ->
                    navigateByBar(navController, index)
                },
                onAddTaskClick = {
                    navController.navigate(Routes.AddTask.routes)
                }
            )
        }
        composable(Routes.AddTask.routes) {
            AddTaskMenu(
                taskDao = database.taskDao(),
                onBack = { navController.popBackStack() },
            )
        }
        composable(Routes.Dailies.routes) {
            DailiesView (
                onItemSelected = { index ->
                    navigateByBar(navController, index)
                },
                onAddDailyClick = {
                    navController.navigate(Routes.AddDaily.routes)
                }
            )
        }
        composable(Routes.AddDaily.routes) {
            AddDailyMenu(
                dailyDao = database.dailyDao(),
                onBack = { navController.popBackStack() },
            )
        }
        composable(Routes.Favorites.routes) {
            FavoritesView(
                onItemSelected = { index ->
                    navigateByBar(navController, index)
                }
            )
        }
        composable(Routes.Profile.routes) {
            ProfileView(
                onItemSelected = { index ->
                    navigateByBar(navController, index)
                },
                onLogout = {
                    navController.navigate(Routes.Login.routes){
                        popUpTo(Routes.Home.routes){inclusive = true}
                    }
                }
            )
        }

    }
}

fun navigateByBar(navController: NavHostController, index: Int) {
    when (index){
        0 -> navController.navigate(Routes.Home.routes)
        1 -> navController.navigate(Routes.Dailies.routes)
        2 -> navController.navigate(Routes.Tasks.routes)
        3 -> navController.navigate(Routes.Profile.routes)
    }
}
