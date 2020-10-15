package com.features.test_app_favoriteimage.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "users_table", indices = arrayOf(Index(value = ["email"], unique = true)))
class DBUsers(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = "phone") var phone: String,
    @ColumnInfo(name = "email") var email: String,
    @ColumnInfo(name = "password") var password: String
)