package com.tamizna.yukbisayuk.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_id")
    val userId: Int,

    @ColumnInfo(name = "user_name")
    val userName: String,

    @ColumnInfo(name = "password")
    val password: String) : Serializable