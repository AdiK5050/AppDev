package org.example.project

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.riasgremory
import org.example.project.viewmodels.Database
import org.jetbrains.compose.resources.imageResource

class Destinations  {
    val database = Database()
    @Composable
    fun CreateDestination() {
        var profilePic: ImageBitmap = imageResource(Res.drawable.riasgremory)
        database.setProfilePic(profilePic)
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
                Messages(SampleData.conversationSample, database, onNavigateToProfile = {
                    navController.navigate(
                        route = UserProfile(name = "Rias")
                    )
                })
            }
            composable<UserProfile> { backStackEntry ->
                val profile: UserProfile = backStackEntry.toRoute()
                ProfileScreen(
                    name = profile.name,
                    database,
                    onNavigateToMessages = {
                        navController.navigate(
                            route = Text
                        )
                    })
            }
            composable<SignUp> { backStackEntry ->
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