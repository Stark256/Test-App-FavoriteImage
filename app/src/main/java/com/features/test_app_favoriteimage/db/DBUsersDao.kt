package com.features.test_app_favoriteimage.db

import androidx.room.Dao
import androidx.room.Query
import com.features.test_app_favoriteimage.db.models.DBUsers

@Dao
interface DBUsersDao : BaseDao<DBUsers>{

    @Query("SELECT * from users_table where email = :email")
    fun getUserByEmail(email: String) : List<DBUsers>
}