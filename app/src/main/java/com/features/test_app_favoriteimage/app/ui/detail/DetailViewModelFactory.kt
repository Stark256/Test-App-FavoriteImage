package com.features.test_app_favoriteimage.app.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.features.test_app_favoriteimage.db.AppDatabase
import javax.inject.Inject

class DetailViewModelFactory @Inject constructor(private val db: AppDatabase)
    : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DetailViewModel(db) as T
    }
}