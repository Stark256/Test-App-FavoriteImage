package com.features.test_app_favoriteimage.sharedPref

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class StorageModule(val context: Context) {

    @Provides
    @Singleton
    fun provideSharedPreferences() : SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }

    @Provides
    @Singleton
    fun provideStorageManager(sharedPreferences: SharedPreferences) : StorageManager {
        return StorageManager(sharedPreferences)
    }

}