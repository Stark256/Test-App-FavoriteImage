package com.features.test_app_favoriteimage.app.ui.detail

import com.features.test_app_favoriteimage.db.AppDatabase
import dagger.Module
import dagger.Provides

@Module
class DetailViewModelModule {

    @Provides
    fun providesDetailViewModelFactory(appDatabase: AppDatabase) : DetailViewModelFactory {
        return DetailViewModelFactory(appDatabase)
    }
}