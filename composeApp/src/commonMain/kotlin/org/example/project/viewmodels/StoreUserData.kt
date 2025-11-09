package org.example.project.viewmodels

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import okio.Path.Companion.toPath


class StoreUserData {


    private val settings = AppSetting.settings

    companion object {
        private  const val KEY_USERNAME = "user_username"
        private const val KEY_PASSWORD = "user_password"
        private const val KEY_LOGGED_IN = "user_logged_in"
    }

    fun saveUserSession(username: String, password: String) {
        settings.putString(KEY_USERNAME, username)
        settings.putString(KEY_PASSWORD, password)
        settings.putBoolean(KEY_LOGGED_IN, true)
    }

    fun clearUserSession() {
        settings.remove(KEY_USERNAME)
        settings.remove(KEY_PASSWORD)
        settings.putBoolean(KEY_LOGGED_IN, false)
    }
    fun putUserLoginInfo(userName: String, password: String) {
        settings.putString(userName, password)
    }
    fun getUserLoginInfo(): List<User> {
        val keys: Set<String> = settings.keys
        val loginInfoList: MutableList<User> = mutableListOf<User>()
        for(key in keys) {
            val userName = key
           val password = settings.getStringOrNull(key).toString()
            loginInfoList.add(User(userName, password))
        }
        return loginInfoList
    }
    fun hasAnyLoginInfo() : Boolean {
        return settings.size != 0
    }

    fun checkUserLoginInfo() : Boolean {
        if(!hasAnyLoginInfo()) false
        val keys: Set<String> = settings.keys
        for(key in keys) {
            val a: String? = settings.getStringOrNull(key)
            val passwordChars = a?.toCharArray()?.toList()
            val hasUpperCase = passwordChars?.any { it.isUpperCase() }
            val hasLowerCase = passwordChars?.any { it.isLowerCase() }
            val hasDigit = passwordChars?.any { it.isDigit() }
            if (a == null) settings.remove(key)
            else if(a.length < 8) settings.remove(key)
            else if(!hasUpperCase!! || !hasLowerCase!! || !hasDigit!!) settings.remove(key)
        }
        return hasAnyLoginInfo()
    }


}
