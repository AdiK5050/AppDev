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
import org.example.project.pages.ContactsPage
import org.example.project.pages.MessagePage
import org.example.project.pages.NewLogin
import org.example.project.pages.NewMessagePage
import org.example.project.pages.NewProfilePage
import org.example.project.pages.NewSignup
import org.example.project.storage.AppDatabase
import org.example.project.storage.UserSession
import org.example.project.viewmodels.MessageViewModel
import org.example.project.viewmodels.SharedViewModel

interface Destination
class Destinations(val appDatabase: AppDatabase,val sharedViewModel: SharedViewModel)  {

    val userSession = UserSession()

    val messageViewModel = MessageViewModel(appDatabase,userSession,sharedViewModel)

    fun getStartDestination(): Destination {
        if (userSession.isLoggedIn()) return ChatList
        return NewLogin
    }
    @Composable
    fun CreateDestination() {

        var startDestination: Destination by remember {mutableStateOf(getStartDestination())}

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
                    sharedViewModel,
                    onNavigateToChatList = {
                        navController.navigate(
                            route = ChatList
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
                    appDatabase,
                    sharedViewModel,
                    userSession,
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
                    onNavigateToChatList = {
                        navController.navigate(
                            route = ChatList
                        )
                    },
                    onNavigateToContacts = {
                        navController.navigate(
                            route = Contacts
                        )
                    },
                    onNavigateToProfile = {
                        navController.navigate(
                            route = NewProfilePage
                        )
                    }
                )
            }
            composable<Contacts> { backStackEntry ->
                ContactsPage(
                    appDatabase,
                    sharedViewModel,
                    onNavigateToChatList = {
                        navController.navigate(
                            route = ChatList
                        )
                    },
                    onNavigateToMessage = {
                        navController.navigate(
                            route = MessagePage
                        )
                    }
                )
            }

            composable<MessagePage> { backStackEntry ->
                NewMessagePage(
                    appDatabase,
                    userSession,
                    sharedViewModel,
                    messageViewModel,
                    onNavigateToProfile = {
                        navController.navigate(
                            route = NewProfilePage
                        )
                    },
                    onNavigateToChatList = {
                        navController.navigate(
                            route = ChatList
                        )
                    }
                )
            }
            composable<NewProfilePage> { backStackEntry ->
                NewProfilePage(
                    appDatabase,
                    sharedViewModel,
                    onNavigateToMessages = {
                        navController.navigate(
                            route = MessagePage
                        )
                    })
            }
        }
    }

}