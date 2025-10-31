package org.example.project

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute

@Composable
fun CreateDestination() {
    val appViewModel : AppViewModel = AppViewModel()
    val navController = rememberNavController()
    NavHost(
        modifier = Modifier.then(Modifier),
        navController = navController,
        startDestination = Login
    )
    {
        composable<Login> { backStackEntry->
            Login(
                onNavigateToMessages = {
                    navController.navigate(
                        route = Text
                    )
                },
                onNavigateToSignup = {
                    navController.navigate(
                        route = SignUp
                    )
                },
                appViewModel = appViewModel
            )
        }
        composable<Text> { backStackEntry->
            val texting: Text = backStackEntry.toRoute()
            Texting(SampleData.conversationSample
                , onNavigateToProfile = {
                    navController.navigate(
                        route = UserProfile(name = "Rias")
                    )
                })
        }
        composable<UserProfile> { backStackEntry ->
            val profile: UserProfile = backStackEntry.toRoute()
            ProfileScreen(name = profile.name,
                onNavigateToMessages = {
                    navController.navigate(
                        route = Text
                    )
                })
        }
        composable<SignUp> { backStackEntry ->
            val signup: SignUp = backStackEntry.toRoute()
            SignUp(
                onNavigateToLogin = {
                navController.navigate(
                    route = Login
                    )
                },
            )
        }
    }
}