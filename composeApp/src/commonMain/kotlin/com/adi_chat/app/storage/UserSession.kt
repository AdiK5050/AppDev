package com.adi_chat.app.storage

import com.adi_chat.app.shared.viewmodels.AppSetting

class UserSession() {
    private val settings = AppSetting.settings

    companion object {
        private  const val KEY_USERNAME = "user_username"
        private const val KEY_PASSWORD = "user_password"
        private const val KEY_USER_ID = "user_id"
        private const val KEY_LOGGED_IN = "user_logged_in"
    }


    fun getUserId(): Int {
        return settings.getInt(KEY_USER_ID, defaultValue = 0)
    }
    fun getUsername(): String {
        return settings.getString(KEY_USERNAME, defaultValue = "")
    }
    fun getPassword(): String {
        return settings.getString(KEY_PASSWORD, defaultValue = "")
    }
    fun saveUserSession(username: String, password: String, userID: Int) {
        settings.putString(KEY_USERNAME, username)
        settings.putString(KEY_PASSWORD, password)
        settings.putInt(KEY_USER_ID, userID)
        settings.putBoolean(KEY_LOGGED_IN, true)
    }
    fun isLoggedIn(): Boolean {
        return settings.getBoolean(KEY_LOGGED_IN, defaultValue = false)
    }

    fun clearUserSession() {
        settings.remove(KEY_USERNAME)
        settings.remove(KEY_PASSWORD)
        settings.remove(KEY_USER_ID)
        settings.putBoolean(KEY_LOGGED_IN, false)
    }


}