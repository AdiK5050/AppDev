package com.adi_chat.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.adi_chat.app.ui.app.chat_list.ChatList
import com.adi_chat.app.ui.app.contact.Contacts
import com.adi_chat.app.ui.app.contact.ContactsPage
import com.adi_chat.app.ui.app.chat.MessagePage
import com.adi_chat.app.ui.app.login.NewLogin
import com.adi_chat.app.ui.app.chat.NewMessagePage
import com.adi_chat.app.ui.app.profile.NewProfilePage
import com.adi_chat.app.ui.app.signup.NewSignup
import com.adi_chat.app.storage.AppDatabase
import com.adi_chat.app.storage.UserSession
import com.adi_chat.app.ui.app.chat.viewmodels.MessageViewModel
import com.adi_chat.app.shared.viewmodels.SharedViewModel

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