package com.features.test_app_favoriteimage.app.ui.auth

import com.features.test_app_favoriteimage.db.AppDatabase
import com.features.test_app_favoriteimage.sharedPref.StorageManager
import dagger.Module
import dagger.Provides

@Module
class AuthViewModelModule {

    @Provides
    fun providesAuthViewModelFactory(storageManager: StorageManager, appDatabase: AppDatabase) : AuthViewModelFactory {
        return AuthViewModelFactory(storageManager, appDatabase)
    }
}