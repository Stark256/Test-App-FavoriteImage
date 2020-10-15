package com.features.test_app_favoriteimage.db.models

import androidx.room.*

@Entity(tableName = "images_table", indices = arrayOf(Index(value = ["image_name"], unique = true)))
class DBImages(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = "image") var image: ByteArray,
    @ColumnInfo(name = "image_name") var imageName: String,
    @ColumnInfo(name = "is_favorite") var isFavorite: Boolean,
    @ColumnInfo(name = "user_id") var userID: Int
)
