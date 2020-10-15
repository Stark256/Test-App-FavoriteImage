package com.features.test_app_favoriteimage.app.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.features.test_app_favoriteimage.sharedPref.StorageManager
import javax.inject.Inject

class SplashViewModelFactory @Inject constructor(private val storageManager: StorageManager)
    : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SplashViewModel(storageManager) as T
    }
}
