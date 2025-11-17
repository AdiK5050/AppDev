package org.example.project

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.IntRect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.riasgremory
import org.example.project.pages.MessagePage
import org.example.project.pages.NewLogin
import org.example.project.pages.NewMessagePage
import org.example.project.pages.NewProfilePage
import org.example.project.pages.NewSignup
import org.example.project.storage.AppDatabase
import org.example.project.storage.Database
import org.example.project.storage.UserSession
import org.example.project.viewmodels.LoginViewModel
import org.example.project.viewmodels.MessageViewModel
import org.example.project.viewmodels.ProfileViewModel
import org.example.project.viewmodels.SignupViewModel
import org.jetbrains.compose.resources.imageResource

interface Destination
class Destinations(appDatabase: AppDatabase)  {

    val database = Database(appDatabase)
    val userSession = UserSession()
    val signupViewModel = SignupViewModel(userSession)
    val loginViewModel = LoginViewModel(userSession)
    val messageViewModel = MessageViewModel(database, userSession)
    val profileViewModel = ProfileViewModel(database)

    @Composable
    fun CreateDestination() {

        var startDestination: Destination by remember {mutableStateOf(userSession.getStartDestination())}

        val navController = rememberNavController()
        NavHost(
            modifier = Modifier.then(Modifier),
            navController = navController,
            startDestination = startDestination
        )
        {
            composable<NewLogin> { backStackEntry ->
                NewLogin(
                    loginViewModel,
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
                    messageViewModel,
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
                    profileViewModel,
                    onNavigateToMessages = {
                        navController.navigate(
                            route = MessagePage
                        )
                    })
            }
            composable<NewSignup> { backStackEntry ->
                NewSignup(
                    signupViewModel,
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