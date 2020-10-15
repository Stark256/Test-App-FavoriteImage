package com.features.test_app_favoriteimage.sharedPref

import android.content.SharedPreferences

class StorageManager(sharedPreferences: SharedPreferences) : PreferenceHelper(sharedPreferences) {
    companion object {
        private const val USER_EMAIL_KEY = "user_email_key"
    }

    fun isActiveUserExist() : Boolean {
        return getStringValue(USER_EMAIL_KEY) != null
    }

    fun getActiveUserEmail() : String? {
        return getStringValue(USER_EMAIL_KEY)
    }

    fun setActiveUser(email: String?) {
        storeValue(USER_EMAIL_KEY, email)
    }

}