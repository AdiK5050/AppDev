package org.example.project.storage

import org.example.project.Destination
import org.example.project.pages.MessagePage
import org.example.project.pages.NewLogin
import org.example.project.viewmodels.AppSetting

class UserSession() {
    private val settings = AppSetting.settings

    companion object {
        private  const val KEY_USERNAME = "user_username"
        private const val KEY_PASSWORD = "user_password"
        private const val KEY_LOGGED_IN = "user_logged_in"
    }

    fun getStartDestination(): Destination {
        if (isLoggedIn()) return MessagePage
        if (settings.getBoolean(KEY_LOGGED_IN, defaultValue = false)) return MessagePage
        return NewLogin
    }
    fun getUsername(): String {
        return settings.getString(KEY_USERNAME, defaultValue = "")
    }
    fun getPassword(): String {
        return settings.getString(KEY_PASSWORD, defaultValue = "")
    }
    fun saveUserSession(username: String, password: String) {
        settings.putString(KEY_USERNAME, username)
        settings.putString(KEY_PASSWORD, password)
        settings.putBoolean(KEY_LOGGED_IN, true)
    }
    fun isLoggedIn(): Boolean {
        return settings.getBoolean(KEY_LOGGED_IN, defaultValue = false)
    }

    fun clearUserSession() {
        settings.remove(KEY_USERNAME)
        settings.remove(KEY_PASSWORD)
        settings.putBoolean(KEY_LOGGED_IN, false)
    }


}