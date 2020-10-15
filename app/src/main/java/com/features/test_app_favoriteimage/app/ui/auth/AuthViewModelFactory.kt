package com.features.test_app_favoriteimage.app.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.features.test_app_favoriteimage.db.AppDatabase
import com.features.test_app_favoriteimage.sharedPref.StorageManager
import javax.inject.Inject

class AuthViewModelFactory @Inject constructor(private val storageManager: StorageManager, private val db: AppDatabase)
    : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AuthViewModel(storageManager, db) as T
    }
}