package com.features.test_app_favoriteimage.app.ui.splash

import com.features.test_app_favoriteimage.sharedPref.StorageManager
import dagger.Module
import dagger.Provides

@Module
class SplashViewModelModule {

    @Provides
    fun providesSplashViewModelFactory(storageManager: StorageManager) : SplashViewModelFactory {
        return SplashViewModelFactory(storageManager)
    }

}

