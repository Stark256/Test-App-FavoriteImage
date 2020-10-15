package com.features.test_app_favoriteimage.app.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.features.test_app_favoriteimage.api.ApiService

import com.features.test_app_favoriteimage.db.AppDatabase
import com.features.test_app_favoriteimage.sharedPref.StorageManager
import javax.inject.Inject

class MainViewModelFactory @Inject constructor(
    private val storageManager: StorageManager,
    private val apiService: ApiService,
    private val db: AppDatabase) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(storageManager, apiService, db) as T
    }
}
