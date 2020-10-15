package com.features.test_app_favoriteimage.app.ui.splash

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.features.test_app_favoriteimage.sharedPref.StorageManager

class SplashViewModel(private val storageManager: StorageManager) : ViewModel() {

    val isActiveUserExist = MutableLiveData<Boolean>()

    fun loadActiveUser() {
        isActiveUserExist.value = storageManager.isActiveUserExist()
    }
}