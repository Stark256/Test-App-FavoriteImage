package com.features.test_app_favoriteimage.sharedPref

import android.content.Context
import android.content.SharedPreferences

abstract class PreferenceHelper(val sharedPreff: SharedPreferences) {

    protected fun getStringValue(key: String): String? = sharedPreff.getString(key, null)

    protected fun getIntValue(key: String) : Int? = sharedPreff.getInt(key, -1)

    protected fun getBooleanValue(key: String) : Boolean = sharedPreff.getBoolean(key, false)

    protected fun <U> storeValue(key: String, value: U?) {
        if (value == null) { sharedPreff.edit().remove(key).apply() }
        else { sharedPreff.edit().putString(key, value.toString()).apply() }

    }

    protected fun storeBoolean(key: String, value: Boolean) {
        sharedPreff.edit().putBoolean(key, value).apply()
    }

}