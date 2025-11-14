package org.example.project

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.riasgremory
import org.example.project.viewmodels.Database
import org.jetbrains.compose.resources.imageResource

class Destinations()  {

    val database = Database()

    @Composable
    fun CreateDestination() {

        val profilePic: ImageBitmap = imageResource(Res.drawable.riasgremory)
        var startDestination: Any by remember {mutableStateOf(database.getStartDestination())}

        database.setProfilePic(profilePic)
        database.initUserLoginInfo()
        database.initMessageHistory()

        val navController = rememberNavController()
        NavHost(
            modifier = Modifier.then(Modifier),
            navController = navController,
            startDestination = startDestination
        )
        {
            composable<NewLogin> { backStackEntry ->
                NewLogin(
                    database = database,
                    onNavigateToMessages = {
                        navController.navigate(
                            route = MessagePage
                        )
                    },
                    onNavigateToSignup = {
                        navController.navigate(
                            route = NewSignup
                        )
                    }
                )
            }
            composable<MessagePage> { backStackEntry ->
                NewMessagePage(
                    database,
                    onNavigateToProfile = {
                    navController.navigate(
                        route = NewProfilePage
                    )
                },
                    onNavigateToLogin = {
                        navController.navigate(
                            route = NewLogin
                        )
                    }
                )
            }
            composable<NewProfilePage> { backStackEntry ->
                NewProfilePage(
                    database,
                    onNavigateToMessages = {
                        navController.navigate(
                            route = MessagePage
                        )
                    })
            }
            composable< NewSignup> { backStackEntry ->
                NewSignup(
                    database = database,
                    onNavigateToLogin = {
                        navController.navigate(
                            route = NewLogin
                        )
                    },
                )
            }
        }
    }
}