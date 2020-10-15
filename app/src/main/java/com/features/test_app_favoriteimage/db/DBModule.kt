package com.features.test_app_favoriteimage.db

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DBModule(private val context: Context, private val dbName: String) {

    @Provides
    @Singleton
    fun provideDatabase() : AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            dbName
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideDBUsersDao(db: AppDatabase) : DBUsersDao {
        return db.getDBUsersDao()
    }

    @Provides
    @Singleton
    fun provideDBImagesDao(db: AppDatabase) : DBImagesDao {
        return db.getDBImagesDao()
    }


}