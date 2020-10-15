package com.features.test_app_favoriteimage.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.features.test_app_favoriteimage.db.models.DBImages
import com.features.test_app_favoriteimage.db.models.DBUsers

@Database(entities = arrayOf(DBUsers::class, DBImages::class), version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getDBImagesDao() : DBImagesDao
    abstract fun getDBUsersDao() : DBUsersDao

}