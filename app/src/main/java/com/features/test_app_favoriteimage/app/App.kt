package com.features.test_app_favoriteimage.app

import android.app.Application
import com.features.test_app_favoriteimage.R
import com.features.test_app_favoriteimage.api.ApiModule
import com.features.test_app_favoriteimage.db.DBModule
import com.features.test_app_favoriteimage.sharedPref.StorageModule

class App : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
            .apiModule(ApiModule(getString(R.string.base_url), getString(R.string.api_key)))
            .appModule(AppModule(this))
            .dBModule(DBModule(this, getString(R.string.db_name)))
            .storageModule(StorageModule(this))
            .build()

        appComponent.inject(this)

    }
}