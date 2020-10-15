package com.features.test_app_favoriteimage.api

import com.features.test_app_favoriteimage.api.models.ImagesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/api/v2/search")
    fun searchImage(
        @Query("q") q: String?,
        @Query("tbm") tbm: String = "isch"
    ) : Call<ImagesResponse>
}