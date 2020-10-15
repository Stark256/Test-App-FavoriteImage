package com.features.test_app_favoriteimage.db

import androidx.room.Dao
import androidx.room.Query
import com.features.test_app_favoriteimage.db.models.DBImages

@Dao
interface DBImagesDao : BaseDao<DBImages>{

    @Query("SELECT * from images_table where id = :imageId")
    fun getImageById(imageId: Int) : List<DBImages>

    @Query("SELECT * from images_table where user_id = :userID")
    fun getImagesByUserID(userID: Int): List<DBImages>
}