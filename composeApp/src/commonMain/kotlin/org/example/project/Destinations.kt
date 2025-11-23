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
import org.example.project.pages.ChatList
import org.example.project.pages.Contacts
import org.example.project.pages.MessagePage
import org.example.project.pages.NewLogin
import org.example.project.pages.NewMessagePage
import org.example.project.pages.NewProfilePage
import org.example.project.pages.NewSignup
import org.example.project.storage.AppDatabase
import org.example.project.storage.UserSession
import org.example.project.viewmodels.ChatListViewModel
import org.example.project.viewmodels.MessageViewModel
import org.example.project.viewmodels.SharedViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

interface Destination
class Destinations(val appDatabase: AppDatabase)  {

    val userSession = UserSession()
    val sharedViewModel = SharedViewModel(userSession, appDatabase)
    val messageViewModel = MessageViewModel(appDatabase,userSession)

    val chatListViewModel = ChatListViewModel(appDatabase, userSession, sharedViewModel)

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
            composable<ChatList> { backStackEntry ->
                ChatList(
                    chatListViewModel,
                    onNavigateToMessage = {
                        navController.navigate(
                            route = MessagePage
                        )
                    },
                    onNavigateToLogin =  {
                        navController.navigate(
                            route = NewLogin
                        )
                    },
                    onNavigateToContacts = {
                        navController.navigate(
                            route = Contacts
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
                    },
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
        }
    }

}