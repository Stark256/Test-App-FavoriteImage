package com.features.test_app_favoriteimage.app.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.features.test_app_favoriteimage.app.ui.common.Executor
import com.features.test_app_favoriteimage.db.AppDatabase
import com.features.test_app_favoriteimage.db.models.DBImages

class DetailViewModel(private val db: AppDatabase) : ViewModel() {
    val image = MutableLiveData<DBImages>()

    private lateinit var loadedImage: DBImages


    fun favoriteItem() {
        Executor.IOThread(Runnable {
            loadedImage.isFavorite = !loadedImage.isFavorite
            db.getDBImagesDao().update(loadedImage)
            image.postValue(loadedImage)

        })
    }

    fun loadImage(id: Int) {
        Executor.IOThread(Runnable {
            val imageR = db.getDBImagesDao().getImageById(id).first()
            loadedImage = imageR
            image.postValue(imageR)
        })
    }
}