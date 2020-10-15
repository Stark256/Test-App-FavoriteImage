package com.features.test_app_favoriteimage.app

import android.app.Application
import com.features.test_app_favoriteimage.api.ApiModule
import com.features.test_app_favoriteimage.api.ApiService
import com.features.test_app_favoriteimage.app.ui.splash.SplashActivity
import com.features.test_app_favoriteimage.app.ui.auth.AuthActivity
import com.features.test_app_favoriteimage.app.ui.auth.AuthViewModelModule
import com.features.test_app_favoriteimage.app.ui.detail.DetailActivity
import com.features.test_app_favoriteimage.app.ui.detail.DetailViewModelModule
import com.features.test_app_favoriteimage.app.ui.main.MainActivity
import com.features.test_app_favoriteimage.app.ui.main.MainViewModelModule
import com.features.test_app_favoriteimage.app.ui.splash.SplashViewModelModule
import com.features.test_app_favoriteimage.db.AppDatabase
import com.features.test_app_favoriteimage.db.DBModule
import com.features.test_app_favoriteimage.sharedPref.StorageModule
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = arrayOf(
    AppModule::class,
    ApiModule::class,
    DBModule::class,
    StorageModule::class,
    SplashViewModelModule::class,
    AuthViewModelModule::class,
    MainViewModelModule::class,
    DetailViewModelModule::class
))
interface AppComponent {

    fun application() : Application
    fun apiService() : ApiService
    fun db() : AppDatabase
    fun storage() : ApiService

    fun inject(splashActivity: SplashActivity)
    fun inject(authActivity: AuthActivity)
    fun inject(mainActivity: MainActivity)
    fun inject(detailsActivity: DetailActivity)

    fun inject(app: Application)

}