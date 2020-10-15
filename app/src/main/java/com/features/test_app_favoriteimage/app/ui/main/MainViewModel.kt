package com.features.test_app_favoriteimage.app.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.features.test_app_favoriteimage.api.ApiService
import com.features.test_app_favoriteimage.api.models.ImageModel
import com.features.test_app_favoriteimage.api.models.ImagesResponse
import com.features.test_app_favoriteimage.app.ui.common.Executor
import com.features.test_app_favoriteimage.app.ui.common.safeLet
import com.features.test_app_favoriteimage.db.AppDatabase
import com.features.test_app_favoriteimage.db.models.DBImages
import com.features.test_app_favoriteimage.db.models.DBUsers
import com.features.test_app_favoriteimage.sharedPref.StorageManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(
    private val storageManager: StorageManager,
    private val api: ApiService,
    private val db: AppDatabase
): ViewModel() {

    val user = MutableLiveData<String>()
    val addNewImage = MutableLiveData<DBImages>()
    val images = MutableLiveData<ArrayList<DBImages>>()
    val loadImage = MutableLiveData<String>()
    val isLoading = MutableLiveData<Boolean>()

    private var activeUser: DBUsers? = null
    private var image: ImageModel? = null

    fun logOut(finish:() -> Unit) {
        storageManager.setActiveUser(null)
        finish.invoke()
    }

    fun favoriteItem(dbImages: DBImages) {
        Executor.IOThread(Runnable {
            db.getDBImagesDao().update(dbImages)
//            loadAllImages()
        })
    }

    fun loadActiveUser() {
        storageManager.getActiveUserEmail()?.let { email ->
            Executor.IOThread(Runnable {
                val dbUsers = db.getDBUsersDao().getUserByEmail(email).first()

                activeUser = dbUsers
                user.postValue(dbUsers.email)
                loadAllImages()
            })
        }
    }

    fun saveImage(byteArray: ByteArray)  {
        safeLet(image, activeUser) { imageModel, userR ->
            Executor.IOThread(Runnable {
                val newImage =
                    DBImages(
                        imageName = imageModel.query,
                        image = byteArray,
                        isFavorite = false,
                        userID = userR.id)
                db.getDBImagesDao().insert(newImage)
//                addNewImage.postValue(newImage)

                isLoading.postValue(false)
                loadAllImages()
            })
        }
    }

    fun searchImage(query: String?, error: () -> Unit) {
        if(!query.isNullOrBlank()) {
            isLoading.value = true
            api.searchImage(query).enqueue(object : Callback<ImagesResponse> {
                override fun onFailure(call: Call<ImagesResponse>, t: Throwable) {
                    error.invoke()
                }

                override fun onResponse(
                    call: Call<ImagesResponse>,
                    response: Response<ImagesResponse>
                ) {
                    response.body()?.let { imagesResponse ->
                        val randomImage = imagesResponse.image_results.random()
                        randomImage.query = query
                        image = randomImage
                        loadImage.value = randomImage.sourceUrl
                    }
                }
            })
        } else {
            error.invoke()
        }
    }

    fun loadAllImages() {
        activeUser?.let { userR ->
            Executor.IOThread(Runnable {
                val arr = ArrayList<DBImages>()
                arr.addAll(db.getDBImagesDao().getImagesByUserID(userR.id))
                arr.sortByDescending { it.id }
                images.postValue(arr)
            })
        }
    }

}