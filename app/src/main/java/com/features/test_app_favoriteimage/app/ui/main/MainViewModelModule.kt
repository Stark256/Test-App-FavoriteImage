package com.features.test_app_favoriteimage.app.ui.main

import com.features.test_app_favoriteimage.api.ApiService
import com.features.test_app_favoriteimage.app.ui.splash.SplashViewModelFactory
import com.features.test_app_favoriteimage.db.AppDatabase
import com.features.test_app_favoriteimage.sharedPref.StorageManager
import dagger.Module
import dagger.Provides

@Module
class MainViewModelModule {

    @Provides
    fun providesMainViewModelFactory(
        storageManager: StorageManager,
        apiService: ApiService,
        db: AppDatabase) : MainViewModelFactory {
        return MainViewModelFactory(storageManager, apiService, db)
    }
}
