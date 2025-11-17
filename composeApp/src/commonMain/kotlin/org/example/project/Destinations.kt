package org.example.project

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.example.project.pages.MessagePage
import org.example.project.pages.NewLogin
import org.example.project.pages.NewMessagePage
import org.example.project.pages.NewProfilePage
import org.example.project.pages.NewSignup
import org.example.project.storage.AppDatabase
import org.example.project.storage.UserSession
import org.example.project.viewmodels.MessageViewModel

interface Destination
class Destinations(val appDatabase: AppDatabase)  {

    val userSession = UserSession()
    val messageViewModel = MessageViewModel(appDatabase,userSession)

    @Composable
    fun CreateDestination() {

        var startDestination: Destination by remember {mutableStateOf(userSession.getStartDestination())}

        val navController = rememberNavController()
        NavHost(
            modifier = Modifier.then(Modifier),
            navController = navController,
            startDestination = MessagePage
        )
        {
            composable<NewLogin> { backStackEntry ->
                NewLogin(
                    userSession,
                    appDatabase,
                    onNavigateToMessages = {
                        navController.navigate(
                            route = MessagePage
                        )
                    }
                ) {
                    navController.navigate(
                        route = NewSignup
                    )
                }
            }
            composable<MessagePage> { backStackEntry ->
                NewMessagePage(
                    onNavigateToProfile = {
                        navController.navigate(
                            route = NewProfilePage
                        )
                    },
                    onNavigateToLogin = {
                        navController.navigate(
                            route = NewLogin
                        )
                    },
                    messageViewModel = messageViewModel,
                )
            }
            composable<NewProfilePage> { backStackEntry ->
                NewProfilePage(
                    appDatabase,
                    onNavigateToMessages = {
                        navController.navigate(
                            route = MessagePage
                        )
                    })
            }
            composable<NewSignup> { backStackEntry ->
                NewSignup(
                    appDatabase,
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