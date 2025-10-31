package org.example.project

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import org.example.project.viewmodels.Database

class Destinations  {
    val database = Database()
    @Composable
    fun CreateDestination() {
        val navController = rememberNavController()
        NavHost(
            modifier = Modifier.then(Modifier),
            navController = navController,
            startDestination = Login
        )
        {
            composable<Login> { backStackEntry ->
                Login(
                    database = database,
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
                )
            }
            composable<Text> { backStackEntry ->
                val texting: Text = backStackEntry.toRoute()
                Messages(SampleData.conversationSample, onNavigateToProfile = {
                    navController.navigate(
                        route = UserProfile(name = "Rias")
                    )
                })
            }
            composable<UserProfile> { backStackEntry ->
                val profile: UserProfile = backStackEntry.toRoute()
                ProfileScreen(
                    name = profile.name,
                    onNavigateToMessages = {
                        navController.navigate(
                            route = Text
                        )
                    })
            }
            composable<SignUp> { backStackEntry ->
                val signup: SignUp = backStackEntry.toRoute()
                SignUp(
                    database = database,
                    onNavigateToLogin = {
                        navController.navigate(
                            route = Login
                        )
                    },
                )
            }
        }
    }
}